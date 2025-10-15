package com.example.ufc.service;

import com.example.ufc.entity.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    List<Category> findAllActive();
    Optional<Category> findById(Long id);
    Category save(Category category);
    void deleteById(Long id);
    long countAll();
}

