package com.example.ufc.service;

import com.example.ufc.entity.Comment;
import com.example.ufc.entity.Post;
import com.example.ufc.entity.User;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment createComment(String content, Post post, User author);
    Optional<Comment> findById(Long id);
    List<Comment> findByPostId(Long postId);
    List<Comment> findByAuthorId(Long authorId);
    void deleteComment(Long commentId);
    long countByPostId(Long postId);

    // Admin methods
    long getTotalCommentCount();
    List<Comment> getAllComments();
    List<Comment> searchComments(String search);
}

