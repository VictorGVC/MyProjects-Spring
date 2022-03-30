package com.victorgvc.multilanguagespring.controller;

import java.util.Optional;

import com.victorgvc.multilanguagespring.model.Project;
import com.victorgvc.multilanguagespring.service.ProjectService;

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
@RequestMapping("/project")
public class ProjectController {
    
    private ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestHeader("Authorization") String authorization, Project project) {
        return service.save(project, authorization);
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
            Optional<Project> project = service.getById(id);
            if(project.isPresent())
                return project.get();
            else
                return new ResponseEntity<>("Project do not found!", HttpStatus.valueOf(500));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @PutMapping("/{id}")
    public Object save(@RequestHeader("Authorization") String authorization, @PathVariable (value = "id") int id, Project project) {
        try {
            project.setId(id);
            return service.save(project, authorization); 
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable (value = "id") int id) {
        try {
            service.delete(id); 
            return new ResponseEntity<>("Project "+id+" deleted!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @PostMapping("/{projectId}/item/{itemId}")
    public ResponseEntity<?> addItem(@RequestHeader("Authorization") String authorization, 
                                    @PathVariable (value = "projectId") int projectId, 
                                    @PathVariable (value = "itemId") int itemId) {
        try {
            service.addItem(projectId, itemId, authorization);
            return new ResponseEntity<>("Item "+itemId+" added!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }
    
}
