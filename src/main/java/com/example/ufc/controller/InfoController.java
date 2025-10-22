package com.example.ufc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for handling informational pages (Rules, FAQ, Contact)
 */
@Controller
@RequestMapping("/info")
public class InfoController {

    @GetMapping("/rules")
    public String showRules() {
        return "info/rules";
    }

    @GetMapping("/faq")
    public String showFaq() {
        return "info/faq";
    }

    @GetMapping("/contact")
    public String showContactForm() {
        return "info/contact";
    }

    @PostMapping("/contact")
    public String submitContactForm(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String subject,
            @RequestParam String message,
            RedirectAttributes redirectAttributes) {

        // Validate inputs
        if (name == null || name.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            subject == null || subject.trim().isEmpty() ||
            message == null || message.trim().isEmpty()) {

            redirectAttributes.addFlashAttribute("errorMessage",
                "Всички полета са задължителни. Моля попълнете формата отново.");
            return "redirect:/info/contact";
        }

        // TODO: In production, send email or save to database
        // For now, we'll just simulate success
        System.out.println("=== Contact Form Submission ===");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
        System.out.println("===============================");

        redirectAttributes.addFlashAttribute("successMessage",
            "Благодарим ви! Вашето съобщение беше получено успешно. Ще се свържем с вас скоро.");

        return "redirect:/info/contact";
    }
}

