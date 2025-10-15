package com.example.ufc.repository;

import com.example.ufc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.isActive = true")
    long countActiveUsers();

    @Query("SELECT COUNT(t) FROM Topic t WHERE t.author.id = :userId")
    long countTopicsByUserId(Long userId);

    @Query("SELECT COUNT(p) FROM Post p WHERE p.author.id = :userId")
    long countPostsByUserId(Long userId);
}