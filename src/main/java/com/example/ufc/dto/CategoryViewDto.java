package com.example.ufc.dto;

public class CategoryViewDto {

    private Long id;
    private String name;
    private String description;
    private String icon;
    private Integer topicCount;
    private Integer postCount;
    private String lastTopicTitle;
    private String lastTopicAuthor;

    public CategoryViewDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(Integer topicCount) {
        this.topicCount = topicCount;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public String getLastTopicTitle() {
        return lastTopicTitle;
    }

    public void setLastTopicTitle(String lastTopicTitle) {
        this.lastTopicTitle = lastTopicTitle;
    }

    public String getLastTopicAuthor() {
        return lastTopicAuthor;
    }

    public void setLastTopicAuthor(String lastTopicAuthor) {
        this.lastTopicAuthor = lastTopicAuthor;
    }
}

