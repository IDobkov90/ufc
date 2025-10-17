package com.example.ufc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for admin user updates
 */
public class UserUpdateDto {

    @NotBlank(message = "Потребителското име не може да бъде празно")
    @Size(min = 3, max = 20, message = "Потребителското име трябва да бъде между 3 и 20 символа")
    private String username;

    @NotBlank(message = "Имейлът не може да бъде празен")
    @Email(message = "Невалиден имейл адрес")
    private String email;

    @Size(max = 500, message = "Биографията не може да надвишава 500 символа")
    private String bio;

    private String role;

    private Boolean enabled;

    public UserUpdateDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}

