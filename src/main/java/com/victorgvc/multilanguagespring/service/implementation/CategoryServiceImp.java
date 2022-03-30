package com.victorgvc.multilanguagespring.service.implementation;

import java.util.List;
import java.util.Optional;

import com.victorgvc.multilanguagespring.model.Category;
import com.victorgvc.multilanguagespring.model.Item;
import com.victorgvc.multilanguagespring.repository.CategoryRepository;
import com.victorgvc.multilanguagespring.repository.ItemRepository;
import com.victorgvc.multilanguagespring.service.CategoryService;
import com.victorgvc.multilanguagespring.utils.Validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImp implements CategoryService{

    private CategoryRepository categoryRepository;
    private ItemRepository itemRepository;

    @Autowired
    public CategoryServiceImp(CategoryRepository categoryRepository, ItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;

    }

    @Override
    public ResponseEntity<?> save(Category category) {
        
        try {
            
            Validations.notExists(category.getName(), "Empty name");
            
            categoryRepository.save(category);

            return new ResponseEntity<String>("Category saved!", HttpStatus.valueOf(201));

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
        }
    }

    @Override
    public List<Category> get() throws Exception {
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Optional<Category> getById(int id) throws Exception {
        try {
            return categoryRepository.findById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void delete(int id) throws Exception{
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void addItem(Item item) throws Exception {
        try {
            Validations.notExists(item.getName(), "Empty name");
            Validations.notExists(item.getCategory(), "Invalid category");

            itemRepository.save(item);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void removeItem(int id) throws Exception {
        try {
            itemRepository.deleteById(id);

        } catch (Exception e) {
            throw e;
        }
    }
    
}
