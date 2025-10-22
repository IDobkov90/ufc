package com.example.ufc.config;

import com.example.ufc.filter.JwtAuthenticationFilter;
import com.example.ufc.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * WebSecurityCustomizer to completely bypass Spring Security for static resources.
     * This ensures CSS, JS, and images are served without any security processing.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico", "/webjars/**");
    }

    /**
     * Mentoring Point: API Security Filter Chain (@Order(1))
     * This chain is specifically for our REST API endpoints, matched by "/api/**".
     * It has a higher precedence (lower order number) to ensure it's evaluated first for API requests.
     * - securityMatcher: Scopes this entire filter chain to paths starting with /api/.
     * - SessionCreationPolicy.STATELESS: We are using JWTs, so we don't need server-side sessions.
     * - csrf.disable(): CSRF protection is not typically needed for stateless APIs consumed by non-browser clients.
     * - addFilterBefore: The JwtAuthenticationFilter is added to process the JWT on each request.
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Mentoring Point: Web UI (Form Login) Security Filter Chain (@Order(2))
     * This chain is for our traditional web application. It has a lower precedence and acts as a catch-all.
     * - It is STATEFUL by default, using HTTP sessions to manage authentication state.
     * - CSRF protection is enabled by default, which is crucial for browser-based interactions.
     * - formLogin(): Configures the form-based login page, success URL, and permits access to the login page.
     * - logout(): Configures the logout behavior, invalidating the session and redirecting the user.
     */
    @Bean
    @Order(2)
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        // Public endpoints for the web application
                        .requestMatchers("/", "/home", "/login", "/user/register").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        // Info pages - public access
                        .requestMatchers("/info/**").permitAll()
                        // Search functionality
                        .requestMatchers("/search").permitAll()
                        // Forum - public viewing, authenticated for creation
                        .requestMatchers("/forum", "/forum/category/**", "/forum/topic/{id:[0-9]+}").permitAll()
                        .requestMatchers("/forum/topic/new").authenticated()
                        // Posts - require authentication for all operations
                        .requestMatchers("/forum/post/**").authenticated()
                        // User profiles - public viewing, authenticated for editing
                        .requestMatchers("/user/profile/{username}").permitAll()
                        .requestMatchers("/user/profile/edit").authenticated()
                        // Role-based access control
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/moderator/**").hasAnyRole("MODERATOR", "ADMIN")
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // Custom login page URL
                        .defaultSuccessUrl("/", true) // Redirect to home page on successful login
                        .permitAll() // Allow everyone to see the login page
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // URL to trigger logout
                        .logoutSuccessUrl("/login?logout") // Redirect after logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .authenticationProvider(authenticationProvider());

        // For H2 Console (development only) - apply to this chain as well
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }
}
