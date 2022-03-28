package com.victorgvc.multilanguagespring.controller;

import com.victorgvc.multilanguagespring.model.Project;
import com.victorgvc.multilanguagespring.service.ProjectService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
    
    private ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @PostMapping(value = "/project")
    public ResponseEntity<?> save(Project user) {
        return service.save(user);
    }

    @GetMapping("/project")
    public Object get() {
        try {
            return service.get();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
