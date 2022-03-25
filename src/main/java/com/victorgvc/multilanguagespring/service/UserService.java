package com.victorgvc.multilanguagespring.service;

import java.util.List;
import java.util.Optional;

import com.victorgvc.multilanguagespring.model.User;

import org.springframework.http.ResponseEntity;

public interface UserService {
    
    ResponseEntity<?> save(User user);
    List<User> get() throws Exception;
    Optional<User> getById(int id) throws Exception;
    void delete(int id) throws Exception;
    String encryptPassword(String password);
}
