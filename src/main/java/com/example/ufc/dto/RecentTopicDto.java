package com.example.ufc.dto;

import com.example.ufc.entity.TopicCategory;
import java.time.LocalDateTime;

/**
 * DTO for displaying recent topics in a compact format
 */
public class RecentTopicDto {
    private Long id;
    private String title;
    private TopicCategory category;
    private String authorUsername;
    private LocalDateTime createdAt;
    private Integer replyCount;
    private Integer viewCount;
    private Boolean isPinned;

    public RecentTopicDto() {
    }

    public RecentTopicDto(Long id, String title, TopicCategory category, String authorUsername,
                          LocalDateTime createdAt, Integer replyCount, Integer viewCount, Boolean isPinned) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.authorUsername = authorUsername;
        this.createdAt = createdAt;
        this.replyCount = replyCount;
        this.viewCount = viewCount;
        this.isPinned = isPinned;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TopicCategory getCategory() {
        return category;
    }

    public void setCategory(TopicCategory category) {
        this.category = category;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Boolean getIsPinned() {
        return isPinned;
    }

    public void setIsPinned(Boolean isPinned) {
        this.isPinned = isPinned;
    }
}

