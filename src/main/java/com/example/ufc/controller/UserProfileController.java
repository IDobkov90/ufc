package com.example.ufc.controller;

import com.example.ufc.dto.UserProfileDto;
import com.example.ufc.dto.UserProfileUpdateDto;
import com.example.ufc.entity.Post;
import com.example.ufc.entity.Topic;
import com.example.ufc.entity.User;
import com.example.ufc.service.PostService;
import com.example.ufc.service.TopicService;
import com.example.ufc.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserProfileController {

    private final UserService userService;
    private final TopicService topicService;
    private final PostService postService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserProfileController(UserService userService, TopicService topicService,
                                PostService postService, ModelMapper modelMapper) {
        this.userService = userService;
        this.topicService = topicService;
        this.postService = postService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/profile/{username}")
    public String viewProfile(@PathVariable String username,
                             @RequestParam(defaultValue = "topics") String tab,
                             @RequestParam(defaultValue = "0") int page,
                             Authentication authentication,
                             Model model) {

        Optional<User> userOpt = userService.findByUsername(username);

        if (userOpt.isEmpty()) {
            return "redirect:/forum?error=userNotFound";
        }

        User user = userOpt.get();
        UserProfileDto profileDto = convertToProfileDto(user);

        // Check if viewing own profile
        boolean isOwnProfile = false;
        if (authentication != null && authentication.isAuthenticated()) {
            isOwnProfile = authentication.getName().equals(username);
        }

        model.addAttribute("profile", profileDto);
        model.addAttribute("isOwnProfile", isOwnProfile);
        model.addAttribute("activeTab", tab);

        // Load tab-specific content
        Pageable pageable = PageRequest.of(page, 10);

        if ("topics".equals(tab)) {
            List<Topic> userTopics = topicService.findAll().stream()
                    .filter(t -> t.getAuthor().getId().equals(user.getId()))
                    .limit(10)
                    .collect(Collectors.toList());
            model.addAttribute("topics", userTopics);
        } else if ("posts".equals(tab)) {
            List<Post> userPosts = postService.findByAuthorId(user.getId()).stream()
                    .limit(10)
                    .collect(Collectors.toList());
            model.addAttribute("posts", userPosts);
        }

        return "user/profile";
    }

    @GetMapping("/profile/edit")
    public String showEditProfile(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileUpdateDto updateDto = new UserProfileUpdateDto();
        updateDto.setBio(user.getBio());
        updateDto.setAvatarUrl(user.getAvatarUrl());

        model.addAttribute("userProfileUpdateDto", updateDto);
        model.addAttribute("username", user.getUsername());

        return "user/profile-edit";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@Valid @ModelAttribute UserProfileUpdateDto updateDto,
                               BindingResult result,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        if (result.hasErrors()) {
            User user = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            model.addAttribute("username", user.getUsername());
            return "user/profile-edit";
        }

        try {
            User user = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            userService.updateProfile(user, updateDto.getBio(), updateDto.getAvatarUrl());

            redirectAttributes.addFlashAttribute("successMessage", "Профилът беше обновен успешно!");
            return "redirect:/user/profile/" + user.getUsername();

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Грешка при обновяване на профила: " + e.getMessage());
            User user = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            model.addAttribute("username", user.getUsername());
            return "user/profile-edit";
        }
    }

    private UserProfileDto convertToProfileDto(User user) {
        UserProfileDto dto = modelMapper.map(user, UserProfileDto.class);
        dto.setRole(user.getRole().name());
        dto.setTopicCount(userService.countUserTopics(user.getId()));
        dto.setPostCount(userService.countUserPosts(user.getId()));
        dto.setReputation(user.getReputation());
        return dto;
    }
}
