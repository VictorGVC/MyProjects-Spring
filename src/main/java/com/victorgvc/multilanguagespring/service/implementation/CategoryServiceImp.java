package com.victorgvc.multilanguagespring.service.implementation;

import java.util.List;
import java.util.Optional;

import com.victorgvc.multilanguagespring.model.Category;
import com.victorgvc.multilanguagespring.repository.CategoryRepository;
import com.victorgvc.multilanguagespring.service.CategoryService;
import com.victorgvc.multilanguagespring.utils.Validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImp implements CategoryService{

    private CategoryRepository repository;

    @Autowired
    public CategoryServiceImp(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<?> save(Category category) {
        
        try {
            
            Validations.notExists(category.getName(), "Empty name");
            
            repository.save(category);

            return new ResponseEntity<String>("Category saved!", HttpStatus.valueOf(201));

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
        }
    }

    @Override
    public List<Category> get() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Category> getById(int id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(int id) throws Exception {
        // TODO Auto-generated method stub
        
    }
    
}
