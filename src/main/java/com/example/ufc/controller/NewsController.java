package com.example.ufc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news")
public class NewsController {

    @GetMapping
    public String newsPage(Model model) {
        model.addAttribute("pageTitle", "Новини - UFC България");
        return "news/index";
    }
}

