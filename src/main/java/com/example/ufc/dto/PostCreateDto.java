package com.example.ufc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostCreateDto {

    @NotBlank(message = "Съдържанието е задължително")
    @Size(min = 3, max = 5000, message = "Съдържанието трябва да бъде между 3 и 5000 символа")
    private String content;

    private Long topicId;

    public PostCreateDto() {
    }

    public PostCreateDto(String content, Long topicId) {
        this.content = content;
        this.topicId = topicId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }
}

