package com.example.ufc.repository;

import com.example.ufc.entity.Topic;
import com.example.ufc.entity.TopicCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    
    Page<Topic> findByCategory(TopicCategory category, Pageable pageable);
    
    @Query("SELECT t FROM Topic t ORDER BY t.isPinned DESC, t.lastPostAt DESC")
    Page<Topic> findAllOrderByPinnedAndLastPost(Pageable pageable);
    
    List<Topic> findTop5ByOrderByCreatedAtDesc();
    
    @Query("SELECT COUNT(t) FROM Topic t")
    long countAllTopics();
}