package com.example.ufc.controller;

import com.example.ufc.dto.RecentTopicDto;
import com.example.ufc.entity.Topic;
import com.example.ufc.service.TopicService;
import com.example.ufc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    
    private final UserService userService;
    private final TopicService topicService;

    @Autowired
    public HomeController(UserService userService, TopicService topicService) {
        this.userService = userService;
        this.topicService = topicService;
    }

    @GetMapping("/")
    public String index(Model model, Authentication authentication) {
        // Add user statistics for the homepage
        long activeUsers = userService.countActiveUsers();
        model.addAttribute("activeUserCount", activeUsers);

        // Fetch recent topics for the sidebar
        List<Topic> recentTopics = topicService.getRecentTopics(5);
        List<RecentTopicDto> recentTopicDtos = recentTopics.stream()
            .map(this::convertToRecentTopicDto)
            .collect(Collectors.toList());
        model.addAttribute("recentTopics", recentTopicDtos);

        // Add total topics and posts count
        long totalTopics = topicService.getTotalTopicCount();
        model.addAttribute("totalTopics", totalTopics);

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

    private RecentTopicDto convertToRecentTopicDto(Topic topic) {
        RecentTopicDto dto = new RecentTopicDto();
        dto.setId(topic.getId());
        dto.setTitle(topic.getTitle());
        dto.setCategory(topic.getCategory());
        dto.setAuthorUsername(topic.getAuthor().getUsername());
        dto.setCreatedAt(topic.getCreatedAt());
        dto.setReplyCount(topic.getPosts() != null ? topic.getPosts().size() : 0);
        dto.setViewCount(topic.getViewCount());
        dto.setIsPinned(topic.getIsPinned());
        return dto;
    }
}