package com.victorgvc.multilanguagespring.service;

import java.util.List;
import java.util.Optional;

import com.victorgvc.multilanguagespring.model.Category;

import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<?> save(Category category);
    List<Category> get() throws Exception;
    Optional<Category> getById(int id) throws Exception;
    void delete(int id) throws Exception;
}
