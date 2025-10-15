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
}