package com.example.ufc.controller;

import com.example.ufc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/members")
public class MembersController {

    private final UserService userService;

    @Autowired
    public MembersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String membersPage(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "20") int size,
                              Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        var users = userService.findAllUsers(pageable);

        model.addAttribute("users", users.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("totalUsers", users.getTotalElements());
        model.addAttribute("pageTitle", "Членове - UFC България");

        return "members/index";
    }
}

