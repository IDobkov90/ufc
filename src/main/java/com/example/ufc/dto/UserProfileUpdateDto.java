package com.example.ufc.dto;

import jakarta.validation.constraints.Size;

public class UserProfileUpdateDto {

    @Size(max = 500, message = "Биографията не може да бъде повече от 500 символа")
    private String bio;

    private String avatarUrl;

    public UserProfileUpdateDto() {
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}

