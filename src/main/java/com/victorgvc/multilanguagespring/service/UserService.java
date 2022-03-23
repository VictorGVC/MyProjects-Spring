package com.victorgvc.multilanguagespring.service;

import java.util.List;

import com.victorgvc.multilanguagespring.model.User;

public interface UserService {
    
    User save(User user);

    List<User> get();

    User getById(int id);

    User delete(int id);

    String encryptPassword(String password);
}
