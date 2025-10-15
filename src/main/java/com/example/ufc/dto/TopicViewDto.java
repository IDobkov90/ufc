package com.example.ufc.dto;

import com.example.ufc.entity.TopicCategory;
import java.time.LocalDateTime;

public class TopicViewDto {

    private Long id;
    private String title;
    private String content;
    private TopicCategory category;
    private Integer viewCount;
    private Integer replyCount;
    private Boolean isPinned;
    private Boolean isLocked;
    private LocalDateTime createdAt;
    private LocalDateTime lastPostAt;
    private String authorUsername;
    private String lastPostUsername;

    public TopicViewDto() {
    }

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

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Boolean getIsPinned() {
        return isPinned;
    }

    public void setIsPinned(Boolean isPinned) {
        this.isPinned = isPinned;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastPostAt() {
        return lastPostAt;
    }

    public void setLastPostAt(LocalDateTime lastPostAt) {
        this.lastPostAt = lastPostAt;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getLastPostUsername() {
        return lastPostUsername;
    }

    public void setLastPostUsername(String lastPostUsername) {
        this.lastPostUsername = lastPostUsername;
    }
}

