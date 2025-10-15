package com.example.ufc.dto;

import com.example.ufc.entity.TopicCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TopicCreateDto {

    @NotBlank(message = "Заглавието е задължително")
    @Size(min = 5, max = 200, message = "Заглавието трябва да бъде между 5 и 200 символа")
    private String title;

    @NotBlank(message = "Съдържанието е задължително")
    @Size(min = 10, message = "Съдържанието трябва да бъде поне 10 символа")
    private String content;

    @NotNull(message = "Категорията е задължителна")
    private TopicCategory category;

    public TopicCreateDto() {
    }

    public TopicCreateDto(String title, String content, TopicCategory category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TopicCategory getCategory() {
        return category;
    }

    public void setCategory(TopicCategory category) {
        this.category = category;
    }
}

