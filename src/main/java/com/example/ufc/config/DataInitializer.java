package com.example.ufc.config;

import com.example.ufc.entity.*;
import com.example.ufc.repository.UserRepository;
import com.example.ufc.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        // Create admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@ufcbg.com");
        admin.setPassword(passwordEncoder.encode("admin123")); // Properly encoded password
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setRole(Role.ADMIN);
        admin.setEmailVerified(true);
        admin.setEnabled(true);
        userRepository.save(admin);

        // Create sample user
        User user = new User();
        user.setUsername("ufcfan");
        user.setEmail("fan@ufcbg.com");
        user.setPassword(passwordEncoder.encode("password123")); // Properly encoded password
        user.setFirstName("UFC");
        user.setLastName("Fan");
        user.setRole(Role.USER);
        user.setEmailVerified(true);
        user.setEnabled(true);
        userRepository.save(user);

        // Create sample topics
        Topic topic1 = new Topic();
        topic1.setTitle("Добре дошли в UFC България!");
        topic1.setContent("Това е първата тема в нашия форум. Споделете вашите мнения за UFC!");
        topic1.setCategory(TopicCategory.GENERAL_UFC);
        topic1.setAuthor(admin);
        topic1.setIsPinned(true);
        topicRepository.save(topic1);

        Topic topic2 = new Topic();
        topic2.setTitle("UFC 296 - Прогнози и анализи");
        topic2.setContent("Какви са вашите прогнози за предстоящото събитие UFC 296?");
        topic2.setCategory(TopicCategory.FIGHT_ANALYSIS);
        topic2.setAuthor(user);
        topicRepository.save(topic2);
    }
}