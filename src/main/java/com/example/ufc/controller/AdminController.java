package com.example.ufc.controller;

import com.example.ufc.dto.UserUpdateDto;
import com.example.ufc.entity.User;
import com.example.ufc.entity.Topic;
import com.example.ufc.entity.Post;
import com.example.ufc.entity.Comment;
import com.example.ufc.service.UserService;
import com.example.ufc.service.TopicService;
import com.example.ufc.service.PostService;
import com.example.ufc.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Admin Controller - Handles all administrative functions
 * Only accessible by users with ADMIN role
 */
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final TopicService topicService;
    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public AdminController(UserService userService, TopicService topicService,
                          PostService postService, CommentService commentService) {
        this.userService = userService;
        this.topicService = topicService;
        this.postService = postService;
        this.commentService = commentService;
    }

    /**
     * Admin Dashboard - Overview with statistics and recent activity
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Collect statistics
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userService.getTotalUserCount());
        stats.put("totalTopics", topicService.getTotalTopicCount());
        stats.put("totalPosts", postService.getTotalPostCount());
        stats.put("totalComments", commentService.getTotalCommentCount());
        stats.put("activeUsers", userService.getActiveUserCount());
        stats.put("bannedUsers", userService.getBannedUserCount());

        // Recent activity
        List<User> recentUsers = userService.getRecentUsers(10);
        List<Topic> recentTopics = topicService.getRecentTopics(10);

        model.addAttribute("stats", stats);
        model.addAttribute("recentUsers", recentUsers);
        model.addAttribute("recentTopics", recentTopics);

        return "admin/dashboard";
    }

    /**
     * User Management - List all users
     */
    @GetMapping("/users")
    public String listUsers(@RequestParam(required = false) String search,
                           @RequestParam(required = false) String role,
                           @RequestParam(required = false) String status,
                           Model model) {
        List<User> users;

        if (search != null && !search.isEmpty()) {
            users = userService.searchUsers(search);
        } else if (role != null && !role.isEmpty()) {
            users = userService.getUsersByRole(role);
        } else if ("banned".equals(status)) {
            users = userService.getBannedUsers();
        } else {
            users = userService.getAllUsers();
        }

        model.addAttribute("users", users);
        model.addAttribute("search", search);
        model.addAttribute("role", role);
        model.addAttribute("status", status);

        return "admin/users";
    }

    /**
     * Edit User
     */
    @GetMapping("/users/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user-edit";
    }

    /**
     * Update User
     */
    @PostMapping("/users/{id}/update")
    public String updateUser(@PathVariable Long id,
                           @ModelAttribute UserUpdateDto userUpdateDto,
                           RedirectAttributes redirectAttributes) {
        try {
            userService.updateUserByAdmin(id, userUpdateDto);
            redirectAttributes.addFlashAttribute("successMessage", "Потребителят е актуализиран успешно!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Грешка при актуализиране: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    /**
     * Ban User
     */
    @PostMapping("/users/{id}/ban")
    public String banUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.banUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "Потребителят е блокиран успешно!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Грешка при блокиране: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    /**
     * Unban User
     */
    @PostMapping("/users/{id}/unban")
    public String unbanUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.unbanUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "Блокирането е премахнато успешно!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Грешка при премахване на блокирането: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    /**
     * Delete User
     */
    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "Потребителят е изтрит успешно!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Грешка при изтриване: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    /**
     * Topic Management - List all topics
     */
    @GetMapping("/topics")
    public String listTopics(@RequestParam(required = false) String search,
                            @RequestParam(required = false) String category,
                            Model model) {
        List<Topic> topics;

        if (search != null && !search.isEmpty()) {
            topics = topicService.searchTopics(search);
        } else if (category != null && !category.isEmpty()) {
            topics = topicService.getTopicsByCategory(category);
        } else {
            topics = topicService.getAllTopics();
        }

        model.addAttribute("topics", topics);
        model.addAttribute("search", search);
        model.addAttribute("category", category);

        return "admin/topics";
    }

    /**
     * Pin Topic
     */
    @PostMapping("/topics/{id}/pin")
    public String pinTopic(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            topicService.pinTopic(id);
            redirectAttributes.addFlashAttribute("successMessage", "Темата е закачена успешно!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Грешка: " + e.getMessage());
        }
        return "redirect:/admin/topics";
    }

    /**
     * Unpin Topic
     */
    @PostMapping("/topics/{id}/unpin")
    public String unpinTopic(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            topicService.unpinTopic(id);
            redirectAttributes.addFlashAttribute("successMessage", "Темата е откачена успешно!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Грешка: " + e.getMessage());
        }
        return "redirect:/admin/topics";
    }

    /**
     * Lock Topic
     */
    @PostMapping("/topics/{id}/lock")
    public String lockTopic(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            topicService.lockTopic(id);
            redirectAttributes.addFlashAttribute("successMessage", "Темата е заключена успешно!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Грешка: " + e.getMessage());
        }
        return "redirect:/admin/topics";
    }

    /**
     * Unlock Topic
     */
    @PostMapping("/topics/{id}/unlock")
    public String unlockTopic(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            topicService.unlockTopic(id);
            redirectAttributes.addFlashAttribute("successMessage", "Темата е отключена успешно!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Грешка: " + e.getMessage());
        }
        return "redirect:/admin/topics";
    }

    /**
     * Delete Topic
     */
    @PostMapping("/topics/{id}/delete")
    public String deleteTopic(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            topicService.deleteTopic(id);
            redirectAttributes.addFlashAttribute("successMessage", "Темата е изтрита успешно!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Грешка при изтриване: " + e.getMessage());
        }
        return "redirect:/admin/topics";
    }

    /**
     * Comment Management - List all comments
     */
    @GetMapping("/comments")
    public String listComments(@RequestParam(required = false) String search, Model model) {
        List<Comment> comments;

        if (search != null && !search.isEmpty()) {
            comments = commentService.searchComments(search);
        } else {
            comments = commentService.getAllComments();
        }

        model.addAttribute("comments", comments);
        model.addAttribute("search", search);

        return "admin/comments";
    }

    /**
     * Delete Comment
     */
    @PostMapping("/comments/{id}/delete")
    public String deleteComment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            commentService.deleteComment(id);
            redirectAttributes.addFlashAttribute("successMessage", "Коментарът е изтрит успешно!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Грешка при изтриване: " + e.getMessage());
        }
        return "redirect:/admin/comments";
    }

    /**
     * Analytics Page
     */
    @GetMapping("/analytics")
    public String analytics(Model model) {
        Map<String, Object> analytics = new HashMap<>();

        // User analytics
        analytics.put("totalUsers", userService.getTotalUserCount());
        analytics.put("activeUsers", userService.getActiveUserCount());
        analytics.put("bannedUsers", userService.getBannedUserCount());
        analytics.put("newUsersToday", userService.getNewUsersToday());
        analytics.put("newUsersThisWeek", userService.getNewUsersThisWeek());
        analytics.put("newUsersThisMonth", userService.getNewUsersThisMonth());

        // Content analytics
        analytics.put("totalTopics", topicService.getTotalTopicCount());
        analytics.put("totalPosts", postService.getTotalPostCount());
        analytics.put("totalComments", commentService.getTotalCommentCount());
        analytics.put("pinnedTopics", topicService.getPinnedTopicCount());
        analytics.put("lockedTopics", topicService.getLockedTopicCount());

        // Top contributors
        analytics.put("topPosters", userService.getTopPosters(10));
        analytics.put("topicsByCategory", topicService.getTopicCountByCategory());

        model.addAttribute("analytics", analytics);

        return "admin/analytics";
    }
}

