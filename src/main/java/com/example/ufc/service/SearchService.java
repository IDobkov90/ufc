package com.example.ufc.service;

import com.example.ufc.dto.SearchResultDto;

public interface SearchService {
    SearchResultDto search(String query, String type);
    SearchResultDto searchTopics(String query);
    SearchResultDto searchPosts(String query);
    SearchResultDto searchUsers(String query);
}

