package com.victorgvc.multilanguagespring.controller;

import java.util.Optional;

import com.victorgvc.multilanguagespring.model.Category;
import com.victorgvc.multilanguagespring.service.CategoryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> save(Category category) {
        return service.save(category);
    }

    @GetMapping
    public Object get() {
        try {
            return service.get();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @GetMapping("/{id}")
    public Object getById(@PathVariable (value = "id") int id) {
        try {

            Optional<Category> category = service.getById(id);
            if(category.isPresent())
                return category.get();
            else
                return new ResponseEntity<>("Category do not found!", HttpStatus.valueOf(500));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> save(@PathVariable (value = "id") int id, Category category) {
        category.setId(id); 
        return service.save(category); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable (value = "id") int id) {
        try {
            service.delete(id); 
            return new ResponseEntity<>("Category "+id+" deleted!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
