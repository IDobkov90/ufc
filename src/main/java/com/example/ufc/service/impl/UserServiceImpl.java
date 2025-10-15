package com.example.ufc.service.impl;

import com.example.ufc.dto.UserRegistrationDto;
import com.example.ufc.entity.Role;
import com.example.ufc.entity.User;
import com.example.ufc.exception.UserAlreadyExistsException;
import com.example.ufc.repository.UserRepository;
import com.example.ufc.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
}