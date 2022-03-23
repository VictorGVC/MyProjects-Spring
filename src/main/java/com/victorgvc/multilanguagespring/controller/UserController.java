package com.victorgvc.multilanguagespring.controller;

import java.util.List;

import com.victorgvc.multilanguagespring.model.User;
import com.victorgvc.multilanguagespring.repository.UserRepository;
import com.victorgvc.multilanguagespring.service.UserService;
import com.victorgvc.multilanguagespring.utils.Validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserController implements UserService{

    private UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        try {
            Validations.notExists(user.getUsername(), "Empty username");
            Validations.notExists(user.getPassword(), "Empty password");
            Validations.notExists(user.getConfirmPassword(), "Invalid password confirmation");
            Validations.notEquals(user.getPassword(), user.getConfirmPassword(), "Password confirmation does not match");

            User userFromDB = repository.findUserByUsername(user.getUsername());

            if(user.getId() == null)
                Validations.exists(userFromDB, "User already exists");
        } catch (Exception e) {
            // TODO return on response an error message
        }


        return null;
    }

    @Override
    public List<User> get() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User getById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User delete(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String encryptPassword(String password) {
        String salt = BCrypt.gensalt(10);
        String passEncoded = BCrypt.hashpw(password, salt);
        return passEncoded;
    }
    
}
