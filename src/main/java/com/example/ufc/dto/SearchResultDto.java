package com.example.ufc.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SearchResultDto {
    private List<TopicSearchResult> topics = new ArrayList<>();
    private List<PostSearchResult> posts = new ArrayList<>();
    private List<UserSearchResult> users = new ArrayList<>();
    private String query;

    public SearchResultDto() {
    }

    // Getters and Setters
    public List<TopicSearchResult> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicSearchResult> topics) {
        this.topics = topics;
    }

    public List<PostSearchResult> getPosts() {
        return posts;
    }

    public void setPosts(List<PostSearchResult> posts) {
        this.posts = posts;
    }

    public List<UserSearchResult> getUsers() {
        return users;
    }

    public void setUsers(List<UserSearchResult> users) {
        this.users = users;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getTotalResults() {
        return topics.size() + posts.size() + users.size();
    }

    // Inner classes
    public static class TopicSearchResult {
        private Long id;
        private String title;
        private String excerpt;
        private String authorUsername;
        private String categoryName;
        private LocalDateTime createdAt;
        private int viewCount;
        private int replyCount;

        public TopicSearchResult() {
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

        public String getExcerpt() {
            return excerpt;
        }

        public void setExcerpt(String excerpt) {
            this.excerpt = excerpt;
        }

        public String getAuthorUsername() {
            return authorUsername;
        }

        public void setAuthorUsername(String authorUsername) {
            this.authorUsername = authorUsername;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }
    }

    public static class PostSearchResult {
        private Long id;
        private String content;
        private String excerpt;
        private String authorUsername;
        private Long topicId;
        private String topicTitle;
        private LocalDateTime createdAt;

        public PostSearchResult() {
        }

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getExcerpt() {
            return excerpt;
        }

        public void setExcerpt(String excerpt) {
            this.excerpt = excerpt;
        }

        public String getAuthorUsername() {
            return authorUsername;
        }

        public void setAuthorUsername(String authorUsername) {
            this.authorUsername = authorUsername;
        }

        public Long getTopicId() {
            return topicId;
        }

        public void setTopicId(Long topicId) {
            this.topicId = topicId;
        }

        public String getTopicTitle() {
            return topicTitle;
        }

        public void setTopicTitle(String topicTitle) {
            this.topicTitle = topicTitle;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }
    }

    public static class UserSearchResult {
        private Long id;
        private String username;
        private String bio;
        private String role;
        private LocalDateTime createdAt;
        private int topicCount;
        private int postCount;

        public UserSearchResult() {
        }

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public int getTopicCount() {
            return topicCount;
        }

        public void setTopicCount(int topicCount) {
            this.topicCount = topicCount;
        }

        public int getPostCount() {
            return postCount;
        }

        public void setPostCount(int postCount) {
            this.postCount = postCount;
        }
    }
}
