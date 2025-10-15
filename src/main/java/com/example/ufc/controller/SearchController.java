package com.example.ufc.controller;

import com.example.ufc.dto.SearchResultDto;
import com.example.ufc.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public String search(@RequestParam(required = false) String q,
                        @RequestParam(required = false, defaultValue = "all") String type,
                        Model model) {

        if (q == null || q.trim().isEmpty()) {
            model.addAttribute("showEmptyState", true);
            return "search/results";
        }

        SearchResultDto results = searchService.search(q, type);

        model.addAttribute("results", results);
        model.addAttribute("query", q);
        model.addAttribute("type", type);
        model.addAttribute("showEmptyState", false);

        return "search/results";
    }
}

