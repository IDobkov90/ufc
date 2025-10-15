package com.example.ufc.controller;

import com.example.ufc.dto.PostCreateDto;
import com.example.ufc.entity.Post;
import com.example.ufc.entity.Topic;
import com.example.ufc.entity.User;
import com.example.ufc.service.PostService;
import com.example.ufc.service.TopicService;
import com.example.ufc.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/forum/post")
public class PostController {

    private final PostService postService;
    private final TopicService topicService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, TopicService topicService, UserService userService) {
        this.postService = postService;
        this.topicService = topicService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute PostCreateDto postCreateDto,
                            BindingResult result,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Моля, въведете валидно съдържание (минимум 3 символа)");
            return "redirect:/forum/topic/" + postCreateDto.getTopicId();
        }

        try {
            User author = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Optional<Topic> topicOpt = topicService.findById(postCreateDto.getTopicId());
            if (topicOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Темата не беше намерена");
                return "redirect:/forum";
            }

            Topic topic = topicOpt.get();

            // Check if topic is locked
            if (topic.getIsLocked()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Темата е заключена и не може да приема нови коментари");
                return "redirect:/forum/topic/" + topic.getId();
            }

            postService.createPost(postCreateDto.getContent(), topic, author);

            redirectAttributes.addFlashAttribute("successMessage", "Коментарът беше добавен успешно!");
            return "redirect:/forum/topic/" + topic.getId();

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Възникна грешка: " + e.getMessage());
            return "redirect:/forum/topic/" + postCreateDto.getTopicId();
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,
                              Authentication authentication,
                              Model model,
                              RedirectAttributes redirectAttributes) {

        Optional<Post> postOpt = postService.findById(id);
        if (postOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Коментарът не беше намерен");
            return "redirect:/forum";
        }

        Post post = postOpt.get();
        User user = userService.findByUsername(authentication.getName()).orElse(null);

        if (!postService.canUserModifyPost(post, user)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Нямате права да редактирате този коментар");
            return "redirect:/forum/topic/" + post.getTopic().getId();
        }

        PostCreateDto postDto = new PostCreateDto();
        postDto.setContent(post.getContent());
        postDto.setTopicId(post.getTopic().getId());

        model.addAttribute("postCreateDto", postDto);
        model.addAttribute("postId", post.getId());
        model.addAttribute("topicId", post.getTopic().getId());
        model.addAttribute("isEdit", true);

        return "forum/post-edit";
    }

    @PostMapping("/edit/{id}")
    public String updatePost(@PathVariable Long id,
                            @Valid @ModelAttribute PostCreateDto postCreateDto,
                            BindingResult result,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("postId", id);
            model.addAttribute("topicId", postCreateDto.getTopicId());
            model.addAttribute("isEdit", true);
            return "forum/post-edit";
        }

        try {
            User user = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Post updatedPost = postService.updatePost(id, postCreateDto.getContent(), user);

            redirectAttributes.addFlashAttribute("successMessage", "Коментарът беше редактиран успешно!");
            return "redirect:/forum/topic/" + updatedPost.getTopic().getId();

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Грешка: " + e.getMessage());
            return "redirect:/forum/topic/" + postCreateDto.getTopicId();
        }
    }

    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes) {

        try {
            Optional<Post> postOpt = postService.findById(id);
            if (postOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Коментарът не беше намерен");
                return "redirect:/forum";
            }

            Post post = postOpt.get();
            Long topicId = post.getTopic().getId();

            User user = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            postService.deletePost(id, user);

            redirectAttributes.addFlashAttribute("successMessage", "Коментарът беше изтрит успешно!");
            return "redirect:/forum/topic/" + topicId;

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Грешка: " + e.getMessage());
            return "redirect:/forum";
        }
    }
}

