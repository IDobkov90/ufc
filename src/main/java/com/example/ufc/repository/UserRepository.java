package com.example.ufc.repository;

import com.example.ufc.entity.Role;
import com.example.ufc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.PageRequest;

import java.util.List;
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

    // Admin repository methods
    long countByIsActiveTrue();
    long countByIsActiveFalse();

    @Query("SELECT COUNT(u) FROM User u WHERE DATE(u.createdAt) = CURRENT_DATE")
    long countNewUsersToday();

    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt >= CURRENT_DATE - 7 DAY")
    long countNewUsersThisWeek();

    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt >= CURRENT_DATE - 30 DAY")
    long countNewUsersThisMonth();

    List<User> findAllByOrderByCreatedAtDesc();

    @Query("SELECT u FROM User u ORDER BY u.createdAt DESC")
    List<User> findTopByOrderByCreatedAtDesc(org.springframework.data.domain.Pageable pageable);

    default List<User> findTopByOrderByCreatedAtDesc(int limit) {
        return findTopByOrderByCreatedAtDesc(PageRequest.of(0, limit));
    }

    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<User> searchByUsernameOrEmail(String search);

    List<User> findByRole(Role role);
    List<User> findByIsActiveFalse();

    @Query("SELECT u FROM User u LEFT JOIN u.topics t GROUP BY u.id ORDER BY COUNT(t) DESC")
    List<User> findTopPosters(org.springframework.data.domain.Pageable pageable);

    default List<User> findTopPosters(int limit) {
        return findTopPosters(PageRequest.of(0, limit));
    }
}