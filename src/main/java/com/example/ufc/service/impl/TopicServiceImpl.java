package com.example.ufc.service.impl;

import com.example.ufc.entity.Topic;
import com.example.ufc.entity.TopicCategory;
import com.example.ufc.entity.User;
import com.example.ufc.repository.TopicRepository;
import com.example.ufc.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Topic> findById(Long id) {
        return topicRepository.findById(id);
    }

    @Override
    public Topic save(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public void deleteById(Long id) {
        topicRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Topic> findByCategory(TopicCategory category, Pageable pageable) {
        return topicRepository.findByCategory(category, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Topic> findAllOrderByPinnedAndLastPost(Pageable pageable) {
        return topicRepository.findAllOrderByPinnedAndLastPost(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Topic> findTop5ByOrderByCreatedAtDesc() {
        return topicRepository.findTop5ByOrderByCreatedAtDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public long countAllTopics() {
        return topicRepository.countAllTopics();
    }

    @Override
    public Topic createTopic(String title, String content, TopicCategory category, User author) {
        Topic topic = new Topic(title, content, category, author);
        return topicRepository.save(topic);
    }

    @Override
    public void incrementViewCount(Long topicId) {
        Optional<Topic> topicOpt = topicRepository.findById(topicId);
        topicOpt.ifPresent(topic -> {
            topic.incrementViewCount();
            topicRepository.save(topic);
        });
    }

    // Admin methods implementation

    @Override
    @Transactional(readOnly = true)
    public long getTotalTopicCount() {
        return topicRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long getPinnedTopicCount() {
        return topicRepository.countByIsPinnedTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public long getLockedTopicCount() {
        return topicRepository.countByIsLockedTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Topic> getAllTopics() {
        return topicRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Topic> getRecentTopics(int limit) {
        return topicRepository.findRecentTopics(limit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Topic> searchTopics(String search) {
        return topicRepository.searchByTitleOrContent(search);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Topic> getTopicsByCategory(String category) {
        return topicRepository.findByCategory(TopicCategory.valueOf(category.toUpperCase()));
    }

    @Override
    public void pinTopic(Long id) {
        Topic topic = topicRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Topic not found with id: " + id));
        topic.setPinned(true);
        topicRepository.save(topic);
    }

    @Override
    public void unpinTopic(Long id) {
        Topic topic = topicRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Topic not found with id: " + id));
        topic.setPinned(false);
        topicRepository.save(topic);
    }

    @Override
    public void lockTopic(Long id) {
        Topic topic = topicRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Topic not found with id: " + id));
        topic.setLocked(true);
        topicRepository.save(topic);
    }

    @Override
    public void unlockTopic(Long id) {
        Topic topic = topicRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Topic not found with id: " + id));
        topic.setLocked(false);
        topicRepository.save(topic);
    }

    @Override
    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.Map<String, Long> getTopicCountByCategory() {
        java.util.Map<String, Long> categoryCounts = new java.util.HashMap<>();
        for (TopicCategory category : TopicCategory.values()) {
            long count = topicRepository.countByCategory(category);
            categoryCounts.put(category.name(), count);
        }
        return categoryCounts;
    }
}