package com.victorgvc.multilanguagespring.service;

import java.util.List;

import com.victorgvc.multilanguagespring.model.User;

import org.springframework.http.ResponseEntity;

public interface UserService {
    
    ResponseEntity<?> save(User user);
    List<User> get();
    User getById(int id);
    User delete(int id);
    String encryptPassword(String password);
}
