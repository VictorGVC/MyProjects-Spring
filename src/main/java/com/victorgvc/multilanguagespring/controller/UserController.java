package com.victorgvc.multilanguagespring.controller;

import com.victorgvc.multilanguagespring.model.User;
import com.victorgvc.multilanguagespring.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> save(@RequestBody User user) {
        return service.save(user);
    }
}
