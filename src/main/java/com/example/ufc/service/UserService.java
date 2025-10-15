package com.example.ufc.service;

import com.example.ufc.dto.UserRegistrationDto;
import com.example.ufc.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    User save(User user);
    void deleteById(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    long countActiveUsers();

    User registerNewUser(UserRegistrationDto registrationDto);

    // Profile methods
    User updateProfile(User user, String bio, String avatarUrl);
    long countUserTopics(Long userId);
    long countUserPosts(Long userId);
}