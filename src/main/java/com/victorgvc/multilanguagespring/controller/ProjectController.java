package com.victorgvc.multilanguagespring.controller;

import java.util.Optional;

import com.victorgvc.multilanguagespring.model.Project;
import com.victorgvc.multilanguagespring.service.ProjectService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
    
    private ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @PostMapping(value = "/project")
    public ResponseEntity<?> save(Project project) {
        return service.save(project);
    }

    @GetMapping("/project")
    public Object get() {
        try {
            return service.get();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @GetMapping("/project/{id}")
    public Object getById(@PathVariable (value = "id") int id) {
        try {
            Optional<Project> project = service.getById(id);
            if(project.isPresent())
                return project.get();
            else
                return new ResponseEntity<>("Project do not found!", HttpStatus.valueOf(500));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
}
