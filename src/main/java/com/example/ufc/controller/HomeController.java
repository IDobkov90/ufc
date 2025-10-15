package com.example.ufc.controller;

import com.example.ufc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model, Authentication authentication) {
        // Add user statistics for the homepage
        long activeUsers = userService.countActiveUsers();
        model.addAttribute("activeUserCount", activeUsers);

        // Check if user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }

        return "index";
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        return index(model, authentication);
    }
}