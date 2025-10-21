package com.example.ufc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analysis")
public class AnalysisController {

    @GetMapping
    public String analysisPage(Model model) {
        model.addAttribute("pageTitle", "Анализи - UFC България");
        return "analysis/index";
    }
}

