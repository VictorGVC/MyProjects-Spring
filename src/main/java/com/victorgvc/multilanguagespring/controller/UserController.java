package com.victorgvc.multilanguagespring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.victorgvc.multilanguagespring.model.User;
import com.victorgvc.multilanguagespring.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> save(User user) {
        return service.save(user);
    }

    @GetMapping("/user")
    public List<?> get() {
        try {
            return service.get();
        } catch (Exception e) {
            List<ResponseEntity<String>> error = new ArrayList<>();
            error.add(new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500)));
            return error;
        }
    }

    @GetMapping("/user/{id}")
    public Object getById(@PathVariable (value = "id") int id) {
        try {
            Optional<User> user = service.getById(id);
            if(user.isPresent())
                return user.get();
            else
                return new ResponseEntity<>("User do not found!", HttpStatus.valueOf(500));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> save(@PathVariable (value = "id") int id, User user) {
        user.setId(id); // TODO remember verify if is the owner of the data to change it
        return service.save(user); 
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> delete(@PathVariable (value = "id") int id) {
        try {
            service.delete(id); 
            return new ResponseEntity<>("User "+id+" deleted!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
