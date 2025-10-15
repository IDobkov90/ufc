package com.example.ufc.controller;

import com.example.ufc.dto.PostCreateDto;
import com.example.ufc.dto.PostViewDto;
import com.example.ufc.dto.TopicCreateDto;
import com.example.ufc.dto.TopicViewDto;
import com.example.ufc.entity.Post;
import com.example.ufc.entity.Topic;
import com.example.ufc.entity.TopicCategory;
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
@RequestMapping("/forum")
public class ForumController {

    private final TopicService topicService;
    private final PostService postService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public ForumController(TopicService topicService, PostService postService, UserService userService, ModelMapper modelMapper) {
        this.topicService = topicService;
        this.postService = postService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String forumHome(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "20") int size,
                            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Topic> topicsPage = topicService.findAllOrderByPinnedAndLastPost(pageable);

        // Map to DTOs
        Page<TopicViewDto> topicDtos = topicsPage.map(this::convertToDto);

        model.addAttribute("topics", topicDtos);
        model.addAttribute("categories", TopicCategory.values());
        model.addAttribute("totalTopics", topicService.countAllTopics());

        return "forum/index";
    }

    @GetMapping("/category/{category}")
    public String viewCategory(@PathVariable TopicCategory category,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int size,
                               Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Topic> topicsPage = topicService.findByCategory(category, pageable);

        // Map to DTOs
        Page<TopicViewDto> topicDtos = topicsPage.map(this::convertToDto);

        model.addAttribute("topics", topicDtos);
        model.addAttribute("category", category);
        model.addAttribute("categoryName", category.getDisplayName());

        return "forum/category";
    }

    @GetMapping("/topic/new")
    public String showCreateTopicForm(Model model) {
        model.addAttribute("topicCreateDto", new TopicCreateDto());
        model.addAttribute("categories", TopicCategory.values());
        return "forum/topic-create";
    }

    @PostMapping("/topic/new")
    public String createTopic(@Valid @ModelAttribute TopicCreateDto topicCreateDto,
                              BindingResult result,
                              Authentication authentication,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", TopicCategory.values());
            return "forum/topic-create";
        }

        try {
            User author = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Topic topic = topicService.createTopic(
                    topicCreateDto.getTitle(),
                    topicCreateDto.getContent(),
                    topicCreateDto.getCategory(),
                    author
            );

            redirectAttributes.addFlashAttribute("successMessage", "Темата беше създадена успешно!");
            return "redirect:/forum/topic/" + topic.getId();
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Възникна грешка при създаването на темата: " + e.getMessage());
            model.addAttribute("categories", TopicCategory.values());
            return "forum/topic-create";
        }
    }

    @GetMapping("/topic/{id}")
    public String viewTopic(@PathVariable Long id,
                           Authentication authentication,
                           Model model) {
        Optional<Topic> topicOpt = topicService.findById(id);

        if (topicOpt.isEmpty()) {
            return "redirect:/forum?error=topicNotFound";
        }

        Topic topic = topicOpt.get();

        // Increment view count
        topicService.incrementViewCount(id);

        // Get all posts for this topic
        List<Post> posts = postService.findByTopicId(id);
        List<PostViewDto> postDtos = posts.stream()
                .map(this::convertPostToDto)
                .collect(Collectors.toList());

        TopicViewDto topicDto = convertToDto(topic);
        model.addAttribute("topic", topicDto);
        model.addAttribute("posts", postDtos);
        model.addAttribute("postCreateDto", new PostCreateDto());

        // Check if current user can modify the topic
        if (authentication != null && authentication.isAuthenticated()) {
            User currentUser = userService.findByUsername(authentication.getName()).orElse(null);
            if (currentUser != null) {
                model.addAttribute("currentUserId", currentUser.getId());
                model.addAttribute("canModifyTopic", topic.canBeModifiedBy(currentUser));
            }
        }

        return "forum/topic-view";
    }

    private TopicViewDto convertToDto(Topic topic) {
        TopicViewDto dto = modelMapper.map(topic, TopicViewDto.class);
        dto.setAuthorUsername(topic.getAuthor().getUsername());
        if (topic.getLastPostUser() != null) {
            dto.setLastPostUsername(topic.getLastPostUser().getUsername());
        }
        return dto;
    }

    private PostViewDto convertPostToDto(Post post) {
        PostViewDto dto = modelMapper.map(post, PostViewDto.class);
        dto.setAuthorUsername(post.getAuthor().getUsername());
        dto.setAuthorId(post.getAuthor().getId());
        dto.setTopicId(post.getTopic().getId());
        dto.setIsEdited(post.getUpdatedAt() != null);
        return dto;
    }
}
