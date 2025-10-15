package com.example.ufc.service.impl;

import com.example.ufc.entity.Post;
import com.example.ufc.entity.Topic;
import com.example.ufc.entity.User;
import com.example.ufc.repository.PostRepository;
import com.example.ufc.service.PostService;
import com.example.ufc.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final TopicService topicService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, TopicService topicService) {
        this.postRepository = postRepository;
        this.topicService = topicService;
    }

    @Override
    public Post createPost(String content, Topic topic, User author) {
        Post post = new Post();
        post.setContent(content);
        post.setTopic(topic);
        post.setAuthor(author);
        post.setCreatedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(post);

        // Update topic statistics
        topic.incrementReplyCount();
        topic.updateLastPost(author);

        return savedPost;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findByTopicId(Long topicId) {
        return postRepository.findByTopicIdOrderByCreatedAtAsc(topicId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> findByTopicId(Long topicId, Pageable pageable) {
        return postRepository.findByTopicId(topicId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findByAuthorId(Long authorId) {
        return postRepository.findByAuthorId(authorId);
    }

    @Override
    public Post updatePost(Long postId, String content, User user) {
        Optional<Post> postOpt = postRepository.findById(postId);

        if (postOpt.isEmpty()) {
            throw new RuntimeException("Post not found");
        }

        Post post = postOpt.get();

        if (!canUserModifyPost(post, user)) {
            throw new RuntimeException("You don't have permission to edit this post");
        }

        post.setContent(content);
        post.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId, User user) {
        Optional<Post> postOpt = postRepository.findById(postId);

        if (postOpt.isEmpty()) {
            throw new RuntimeException("Post not found");
        }

        Post post = postOpt.get();

        if (!canUserModifyPost(post, user)) {
            throw new RuntimeException("You don't have permission to delete this post");
        }

        Topic topic = post.getTopic();
        postRepository.delete(post);

        // Update topic reply count
        topic.decrementReplyCount();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canUserModifyPost(Post post, User user) {
        if (user == null) {
            return false;
        }

        // User can modify their own posts or if they're a moderator/admin
        return post.getAuthor().getId().equals(user.getId()) || user.isModerator();
    }

    @Override
    @Transactional(readOnly = true)
    public long countByTopicId(Long topicId) {
        return postRepository.countByTopicId(topicId);
    }
}
