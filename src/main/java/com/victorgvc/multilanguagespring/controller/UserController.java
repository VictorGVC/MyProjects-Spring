package com.victorgvc.multilanguagespring.controller;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> save(User user) {
        return service.save(user, "");
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
            Optional<User> user = service.getById(id);
            if(user.isPresent())
                return user.get();
            else
                return new ResponseEntity<>("User do not found!", HttpStatus.valueOf(500));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> save(@RequestHeader("Authorization") String authorization, @PathVariable (value = "id") int id, User user) {
        user.setId(id);
        return service.save(user, authorization); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable (value = "id") int id) {
        try {
            service.delete(id); 
            return new ResponseEntity<>("User "+id+" deleted!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
