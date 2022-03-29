package com.victorgvc.multilanguagespring.controller;

import com.victorgvc.multilanguagespring.model.Category;
import com.victorgvc.multilanguagespring.service.CategoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    private CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping(value = "/category")
    public ResponseEntity<?> save(Category category) {
        return service.save(category);
    }
}
