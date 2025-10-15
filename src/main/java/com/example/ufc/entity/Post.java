package com.example.ufc.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts", indexes = {
        @Index(name = "idx_post_topic", columnList = "topic_id"),
        @Index(name = "idx_post_author", columnList = "author_id"),
        @Index(name = "idx_post_created_at", columnList = "created_at")
})
public class Post extends BaseEntity {

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_edited", nullable = false)
    private Boolean isEdited = false;

    @Column(name = "edited_at")
    private LocalDateTime editedAt;

    @Column(name = "edit_reason")
    private String editReason;

    @Column(name = "like_count", nullable = false)
    private Integer likeCount = 0;

    @Column(name = "quote_count", nullable = false)
    private Integer quoteCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quoted_post_id")
    private Post quotedPost;

    // Constructors
    public Post() {}

    public Post(String content, Topic topic, User author) {
        this.content = content;
        this.topic = topic;
        this.author = author;
    }

    public Post(String content, Topic topic, User author, Post quotedPost) {
        this(content, topic, author);
        this.quotedPost = quotedPost;
        if (quotedPost != null) {
            quotedPost.incrementQuoteCount();
        }
    }

    // Business methods
    public void edit(String newContent, String reason) {
        this.content = newContent;
        this.isEdited = true;
        this.editedAt = LocalDateTime.now();
        this.editReason = reason;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    public void incrementQuoteCount() {
        this.quoteCount++;
    }

    public boolean canBeModifiedBy(User user) {
        return user.equals(this.author) || user.isModerator();
    }

    public boolean isQuote() {
        return quotedPost != null;
    }

    public String getShortContent(int maxLength) {
        if (content.length() <= maxLength) {
            return content;
        }
        return content.substring(0, maxLength) + "...";
    }

    // Getters and Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsEdited() {
        return isEdited;
    }

    public void setIsEdited(Boolean isEdited) {
        this.isEdited = isEdited;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(LocalDateTime editedAt) {
        this.editedAt = editedAt;
    }

    public String getEditReason() {
        return editReason;
    }

    public void setEditReason(String editReason) {
        this.editReason = editReason;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getQuoteCount() {
        return quoteCount;
    }

    public void setQuoteCount(Integer quoteCount) {
        this.quoteCount = quoteCount;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Post getQuotedPost() {
        return quotedPost;
    }

    public void setQuotedPost(Post quotedPost) {
        this.quotedPost = quotedPost;
    }
}