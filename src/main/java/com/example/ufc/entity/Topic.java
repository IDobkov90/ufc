package com.example.ufc.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topics", indexes = {
        @Index(name = "idx_topic_category", columnList = "category"),
        @Index(name = "idx_topic_created_at", columnList = "created_at"),
        @Index(name = "idx_topic_last_post_at", columnList = "last_post_at"),
        @Index(name = "idx_topic_pinned", columnList = "is_pinned")
})
public class Topic extends BaseEntity {

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private TopicCategory category;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount = 0;

    @Column(name = "reply_count", nullable = false)
    private Integer replyCount = 0;

    @Column(name = "is_pinned", nullable = false)
    private Boolean isPinned = false;

    @Column(name = "is_locked", nullable = false)
    private Boolean isLocked = false;

    @Column(name = "last_post_at")
    private LocalDateTime lastPostAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_post_user_id")
    private User lastPostUser;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    // Constructors
    public Topic() {
        this.lastPostAt = LocalDateTime.now();
    }

    public Topic(String title, String content, TopicCategory category, User author) {
        this();
        this.title = title;
        this.content = content;
        this.category = category;
        this.author = author;
        this.lastPostUser = author;
    }

    // Business methods
    public void incrementViewCount() {
        this.viewCount++;
    }

    public void incrementReplyCount() {
        this.replyCount++;
        this.lastPostAt = LocalDateTime.now();
    }

    public void decrementReplyCount() {
        if (this.replyCount > 0) {
            this.replyCount--;
        }
    }

    public void updateLastPost(User user) {
        this.lastPostUser = user;
        this.lastPostAt = LocalDateTime.now();
    }

    public boolean canBeModifiedBy(User user) {
        return user.equals(this.author) || user.isModerator();
    }

    public boolean isActive() {
        return !isLocked;
    }

    // Getters and Setters
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

    public LocalDateTime getLastPostAt() {
        return lastPostAt;
    }

    public void setLastPostAt(LocalDateTime lastPostAt) {
        this.lastPostAt = lastPostAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getLastPostUser() {
        return lastPostUser;
    }

    public void setLastPostUser(User lastPostUser) {
        this.lastPostUser = lastPostUser;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}