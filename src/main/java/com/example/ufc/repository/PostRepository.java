package com.example.ufc.repository;

import com.example.ufc.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTopicId(Long topicId);

    Page<Post> findByTopicId(Long topicId, Pageable pageable);

    List<Post> findByTopicIdOrderByCreatedAtAsc(Long topicId);

    List<Post> findByAuthorId(Long authorId);

    long countByTopicId(Long topicId);
}