package com.example.ufc.repository;

import com.example.ufc.entity.Topic;
import com.example.ufc.entity.TopicCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    // Admin repository methods
    long countByIsPinnedTrue();
    long countByIsLockedTrue();
    long countByCategory(TopicCategory category);

    List<Topic> findAllByOrderByCreatedAtDesc();
    List<Topic> findByCategory(TopicCategory category);

    @Query("SELECT t FROM Topic t ORDER BY t.createdAt DESC")
    List<Topic> findRecentTopics(Pageable pageable);

    default List<Topic> findRecentTopics(int limit) {
        return findRecentTopics(PageRequest.of(0, limit));
    }

    @Query("SELECT t FROM Topic t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(t.content) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Topic> searchByTitleOrContent(String search);
}