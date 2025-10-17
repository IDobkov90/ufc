package com.example.ufc.service;

import com.example.ufc.entity.Post;
import com.example.ufc.entity.Topic;
import com.example.ufc.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post createPost(String content, Topic topic, User author);
    Optional<Post> findById(Long id);
    List<Post> findByTopicId(Long topicId);
    Page<Post> findByTopicId(Long topicId, Pageable pageable);
    List<Post> findByAuthorId(Long authorId);
    Post updatePost(Long postId, String content, User user);
    void deletePost(Long postId, User user);
    boolean canUserModifyPost(Post post, User user);
    long countByTopicId(Long topicId);

    // Admin methods
    long getTotalPostCount();
}
