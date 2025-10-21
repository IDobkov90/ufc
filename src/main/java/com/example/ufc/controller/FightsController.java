package com.example.ufc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fights")
public class FightsController {

    @GetMapping
    public String fightsPage(Model model) {
        model.addAttribute("pageTitle", "Боеве - UFC България");
        return "fights/index";
    }
}

