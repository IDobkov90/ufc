package com.example.ufc.repository;

import com.example.ufc.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostIdOrderByCreatedAtAsc(Long postId);
    List<Comment> findByAuthorIdOrderByCreatedAtDesc(Long authorId);
    long countByPostId(Long postId);

    // Admin repository methods
    List<Comment> findAllByOrderByCreatedAtDesc();

    @Query("SELECT c FROM Comment c WHERE LOWER(c.content) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Comment> searchByContent(String search);
}

