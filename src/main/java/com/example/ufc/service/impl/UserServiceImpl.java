package com.example.ufc.service.impl;

import com.example.ufc.dto.UserRegistrationDto;
import com.example.ufc.entity.Role;
import com.example.ufc.entity.User;
import com.example.ufc.exception.UserAlreadyExistsException;
import com.example.ufc.repository.UserRepository;
import com.example.ufc.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public long countActiveUsers() {
        return userRepository.countActiveUsers();
    }

    @Override
    @Transactional
    public User registerNewUser(UserRegistrationDto registrationDto) {
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new UserAlreadyExistsException("username", "Username is already taken");
        }

        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new UserAlreadyExistsException("email", "Email is already registered");
        }

        User user = modelMapper.map(registrationDto, User.class);
        setDefaultUserProperties(user, registrationDto.getPassword());

        User savedUser = userRepository.save(user);
        logger.info("New user registered: {}", savedUser.getUsername());

        return savedUser;
    }

    private void setDefaultUserProperties(User user, String rawPassword) {
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(Role.USER);
        user.setEmailVerified(false);
    }

    @Override
    @Transactional
    public User updateProfile(User user, String bio, String avatarUrl) {
        user.setBio(bio);
        user.setAvatarUrl(avatarUrl);
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public long countUserTopics(Long userId) {
        return userRepository.countTopicsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countUserPosts(Long userId) {
        return userRepository.countPostsByUserId(userId);
    }

    // Admin methods implementation

    @Override
    @Transactional(readOnly = true)
    public long getTotalUserCount() {
        return userRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long getActiveUserCount() {
        return userRepository.countByIsActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public long getBannedUserCount() {
        return userRepository.countByIsActiveFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public long getNewUsersToday() {
        return userRepository.countNewUsersToday();
    }

    @Override
    @Transactional(readOnly = true)
    public long getNewUsersThisWeek() {
        return userRepository.countNewUsersThisWeek();
    }

    @Override
    @Transactional(readOnly = true)
    public long getNewUsersThisMonth() {
        return userRepository.countNewUsersThisMonth();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getRecentUsers(int limit) {
        return userRepository.findTopByOrderByCreatedAtDesc(limit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> searchUsers(String search) {
        return userRepository.searchByUsernameOrEmail(search);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(Role.valueOf(role.toUpperCase()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getBannedUsers() {
        return userRepository.findByIsActiveFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    @Transactional
    public void updateUserByAdmin(Long id, com.example.ufc.dto.UserUpdateDto userUpdateDto) {
        User user = getUserById(id);

        // Check if username is being changed and if it's already taken
        if (!user.getUsername().equals(userUpdateDto.getUsername()) &&
            userRepository.existsByUsername(userUpdateDto.getUsername())) {
            throw new UserAlreadyExistsException("username", "Username is already taken");
        }

        // Check if email is being changed and if it's already taken
        if (!user.getEmail().equals(userUpdateDto.getEmail()) &&
            userRepository.existsByEmail(userUpdateDto.getEmail())) {
            throw new UserAlreadyExistsException("email", "Email is already registered");
        }

        user.setUsername(userUpdateDto.getUsername());
        user.setEmail(userUpdateDto.getEmail());
        user.setBio(userUpdateDto.getBio());

        if (userUpdateDto.getRole() != null) {
            user.setRole(Role.valueOf(userUpdateDto.getRole().toUpperCase()));
        }

        if (userUpdateDto.getEnabled() != null) {
            user.setEnabled(userUpdateDto.getEnabled());
        }

        userRepository.save(user);
        logger.info("User {} updated by admin", user.getUsername());
    }

    @Override
    @Transactional
    public void banUser(Long id) {
        User user = getUserById(id);
        user.setEnabled(false);
        userRepository.save(user);
        logger.info("User {} has been banned", user.getUsername());
    }

    @Override
    @Transactional
    public void unbanUser(Long id) {
        User user = getUserById(id);
        user.setEnabled(true);
        userRepository.save(user);
        logger.info("User {} has been unbanned", user.getUsername());
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);
        String username = user.getUsername();
        userRepository.delete(user);
        logger.info("User {} has been deleted", username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getTopPosters(int limit) {
        return userRepository.findTopPosters(limit);
    }
}