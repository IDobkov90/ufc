package com.example.ufc.service.impl;

import com.example.ufc.dto.SearchResultDto;
import com.example.ufc.entity.Post;
import com.example.ufc.entity.Topic;
import com.example.ufc.entity.User;
import com.example.ufc.repository.PostRepository;
import com.example.ufc.repository.TopicRepository;
import com.example.ufc.repository.UserRepository;
import com.example.ufc.service.SearchService;
import com.example.ufc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class SearchServiceImpl implements SearchService {

    private final TopicRepository topicRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public SearchServiceImpl(TopicRepository topicRepository, PostRepository postRepository,
                            UserRepository userRepository, UserService userService) {
        this.topicRepository = topicRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public SearchResultDto search(String query, String type) {
        if (query == null || query.trim().isEmpty()) {
            return new SearchResultDto();
        }

        query = query.trim();

        SearchResultDto result = new SearchResultDto();
        result.setQuery(query);

        if (type == null || "all".equals(type)) {
            // Search all types
            result = searchAll(query);
        } else if ("topics".equals(type)) {
            result = searchTopics(query);
        } else if ("posts".equals(type)) {
            result = searchPosts(query);
        } else if ("users".equals(type)) {
            result = searchUsers(query);
        }

        return result;
    }

    private SearchResultDto searchAll(String query) {
        SearchResultDto result = new SearchResultDto();
        result.setQuery(query);

        // Search topics
        List<Topic> topics = topicRepository.findAll().stream()
                .filter(t -> matchesTopic(t, query))
                .limit(10)
                .collect(Collectors.toList());

        result.setTopics(topics.stream()
                .map(this::convertToTopicSearchResult)
                .collect(Collectors.toList()));

        // Search posts
        List<Post> posts = postRepository.findAll().stream()
                .filter(p -> matchesPost(p, query))
                .limit(10)
                .collect(Collectors.toList());

        result.setPosts(posts.stream()
                .map(this::convertToPostSearchResult)
                .collect(Collectors.toList()));

        // Search users
        List<User> users = userRepository.findAll().stream()
                .filter(u -> matchesUser(u, query))
                .limit(10)
                .collect(Collectors.toList());

        result.setUsers(users.stream()
                .map(this::convertToUserSearchResult)
                .collect(Collectors.toList()));

        return result;
    }

    @Override
    public SearchResultDto searchTopics(String query) {
        SearchResultDto result = new SearchResultDto();
        result.setQuery(query);

        List<Topic> topics = topicRepository.findAll().stream()
                .filter(t -> matchesTopic(t, query))
                .limit(50)
                .collect(Collectors.toList());

        result.setTopics(topics.stream()
                .map(this::convertToTopicSearchResult)
                .collect(Collectors.toList()));

        return result;
    }

    @Override
    public SearchResultDto searchPosts(String query) {
        SearchResultDto result = new SearchResultDto();
        result.setQuery(query);

        List<Post> posts = postRepository.findAll().stream()
                .filter(p -> matchesPost(p, query))
                .limit(50)
                .collect(Collectors.toList());

        result.setPosts(posts.stream()
                .map(this::convertToPostSearchResult)
                .collect(Collectors.toList()));

        return result;
    }

    @Override
    public SearchResultDto searchUsers(String query) {
        SearchResultDto result = new SearchResultDto();
        result.setQuery(query);

        List<User> users = userRepository.findAll().stream()
                .filter(u -> matchesUser(u, query))
                .limit(50)
                .collect(Collectors.toList());

        result.setUsers(users.stream()
                .map(this::convertToUserSearchResult)
                .collect(Collectors.toList()));

        return result;
    }

    private boolean matchesTopic(Topic topic, String query) {
        String lowerQuery = query.toLowerCase();
        return topic.getTitle().toLowerCase().contains(lowerQuery) ||
               (topic.getContent() != null && topic.getContent().toLowerCase().contains(lowerQuery));
    }

    private boolean matchesPost(Post post, String query) {
        String lowerQuery = query.toLowerCase();
        return post.getContent().toLowerCase().contains(lowerQuery);
    }

    private boolean matchesUser(User user, String query) {
        String lowerQuery = query.toLowerCase();
        return user.getUsername().toLowerCase().contains(lowerQuery) ||
               (user.getBio() != null && user.getBio().toLowerCase().contains(lowerQuery));
    }

    private SearchResultDto.TopicSearchResult convertToTopicSearchResult(Topic topic) {
        SearchResultDto.TopicSearchResult result = new SearchResultDto.TopicSearchResult();
        result.setId(topic.getId());
        result.setTitle(topic.getTitle());
        result.setExcerpt(createExcerpt(topic.getContent(), 150));
        result.setAuthorUsername(topic.getAuthor().getUsername());
        result.setCategoryName(topic.getCategory().getDisplayName());
        result.setCreatedAt(topic.getCreatedAt());
        result.setViewCount(topic.getViewCount());
        result.setReplyCount(topic.getReplyCount());
        return result;
    }

    private SearchResultDto.PostSearchResult convertToPostSearchResult(Post post) {
        SearchResultDto.PostSearchResult result = new SearchResultDto.PostSearchResult();
        result.setId(post.getId());
        result.setContent(post.getContent());
        result.setExcerpt(createExcerpt(post.getContent(), 150));
        result.setAuthorUsername(post.getAuthor().getUsername());
        result.setTopicId(post.getTopic().getId());
        result.setTopicTitle(post.getTopic().getTitle());
        result.setCreatedAt(post.getCreatedAt());
        return result;
    }

    private SearchResultDto.UserSearchResult convertToUserSearchResult(User user) {
        SearchResultDto.UserSearchResult result = new SearchResultDto.UserSearchResult();
        result.setId(user.getId());
        result.setUsername(user.getUsername());
        result.setBio(user.getBio());
        result.setRole(user.getRole().name());
        result.setCreatedAt(user.getCreatedAt());
        result.setTopicCount((int) userService.countUserTopics(user.getId()));
        result.setPostCount((int) userService.countUserPosts(user.getId()));
        return result;
    }

    private String createExcerpt(String content, int maxLength) {
        if (content == null || content.isEmpty()) {
            return "";
        }
        if (content.length() <= maxLength) {
            return content;
        }
        return content.substring(0, maxLength) + "...";
    }
}

