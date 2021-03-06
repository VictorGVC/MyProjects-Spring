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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@RequestMapping("/project")
public class ProjectController {
    
    private ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    // Notations for documentation
    @Operation(summary = "Save a new project", security = { @SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Project saved!",
            content = @Content(schema = @Schema( type = "string", example = "Project saved!"))),
        @ApiResponse(responseCode = "400", description = "Client side exception",
            content = @Content(schema = @Schema( type = "string", example = "Empty name")))})

    // Save a new project
    @PostMapping
    public ResponseEntity<?> save(@RequestHeader("Authorization") String authorization, 
                                    @Parameter(description="Password and confirmPassword fields have to be the same!", 
                                    required=true, schema=@Schema(implementation = Project.class))Project project) {
        return service.save(project, authorization);
    }

    // Get all projects
    @GetMapping
    public Object get() {
        try {
            return service.get();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    // Get project by id
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

    // Update project
    @PutMapping("/{id}")
    public Object save(@RequestHeader("Authorization") String authorization, @PathVariable (value = "id") int id, Project project) {
        try {
            project.setId(id);
            return service.save(project, authorization); 
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    @Operation(summary = "Delete project by id", description = "Only admin users can use this endpoint", security = { @SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Project deleted!",
            content = @Content(schema = @Schema( type = "string", example = "Project 1 deleted!"))),
        @ApiResponse(responseCode = "500", description = "Server side exception",
            content = @Content)
    })

    // Delete project
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable (value = "id") int id) {
        try {
            service.delete(id); 
            return new ResponseEntity<>("Project "+id+" deleted!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    // Add utem to project
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
