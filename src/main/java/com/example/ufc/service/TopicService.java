package com.example.ufc.service;

import com.example.ufc.entity.Topic;
import com.example.ufc.entity.TopicCategory;
import com.example.ufc.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TopicService {
    List<Topic> findAll();
    Optional<Topic> findById(Long id);
    Topic save(Topic topic);
    void deleteById(Long id);
    Page<Topic> findByCategory(TopicCategory category, Pageable pageable);
    Page<Topic> findAllOrderByPinnedAndLastPost(Pageable pageable);
    List<Topic> findTop5ByOrderByCreatedAtDesc();
    long countAllTopics();
    Topic createTopic(String title, String content, TopicCategory category, User author);
    void incrementViewCount(Long topicId);

    // Admin methods
    long getTotalTopicCount();
    long getPinnedTopicCount();
    long getLockedTopicCount();
    List<Topic> getAllTopics();
    List<Topic> getRecentTopics(int limit);
    List<Topic> searchTopics(String search);
    List<Topic> getTopicsByCategory(String category);
    void pinTopic(Long id);
    void unpinTopic(Long id);
    void lockTopic(Long id);
    void unlockTopic(Long id);
    void deleteTopic(Long id);
    Map<String, Long> getTopicCountByCategory();
}