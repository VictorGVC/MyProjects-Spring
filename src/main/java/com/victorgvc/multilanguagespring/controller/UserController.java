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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/user")
public class UserController {
   
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // Notations for documentation
    @Operation(summary = "Register user")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "User saved!",
            content = @Content(schema = @Schema( type = "string", example = "User saved!"))),
        @ApiResponse(responseCode = "400", description = "Client side exception",
            content = @Content(schema = @Schema( type = "string", example = "Empty username")))})
    
    // Register user
    @PostMapping
    public ResponseEntity<?> save(@Parameter(description="Password and confirmPassword fields have to be the same!", 
                                    required=true, schema=@Schema(implementation = User.class))User user) {
        return service.save(user, "");
    }

    // Notations for documentation
    @Operation(summary = "Get a list of users", security = { @SecurityRequirement(name = "Bearer") })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successfully obtained users!",content = {
            @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = User.class))
            )
    }),
        @ApiResponse(responseCode = "500", description = "Server side exception", 
            content = @Content)})

    // Get all users
    @GetMapping
    public Object get() {
        try {
            return service.get();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    // Notations for documentation
    @Operation(summary = "Get user by id", security = { @SecurityRequirement(name = "Bearer") })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successfully obtained the user!",content = {
            @Content(schema = @Schema(implementation = User.class))
        }),
        @ApiResponse(responseCode = "400", description = "Client side exception", 
            content = @Content(schema = @Schema( type = "string", example = "User not found!")))})

    // Get user by id
    @GetMapping("/{id}")
    public Object getById(@PathVariable (value = "id") int id) {
        try {
            Optional<User> user = service.getById(id);
            if(user.isPresent())
                return user.get();
            else
                return new ResponseEntity<>("User not found!", HttpStatus.valueOf(500));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(500));
        }
    }

    // Notations for documentation
    @Operation(summary = "Update user informations by id", security = { @SecurityRequirement(name = "Bearer") })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "User saved!",
            content = @Content(schema = @Schema( type = "string", example = "User saved!"))),
        @ApiResponse(responseCode = "400", description = "Client side exception", 
            content = @Content(schema = @Schema( type = "string", example = "Empty username")))})

    // Update user by id 
    @PutMapping("/{id}")
    public ResponseEntity<?> save(@RequestHeader("Authorization") String authorization, @PathVariable (value = "id") int id, 
                                    @Parameter(description="Password and confirmPassword fields have to be the same!", 
                                        required=true, schema=@Schema(implementation = User.class))User user) {
        user.setId(id);
        return service.save(user, authorization); 
    }

    // Notations for documentation
    @Operation(summary = "Delete user by id", description = "Only admin users can use this endpoint", security = { @SecurityRequirement(name = "Bearer") })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "User deleted!",
            content = @Content(schema = @Schema( type = "string", example = "User 3 deleted!"))),
        @ApiResponse(responseCode = "500", description = "Server side exception", 
            content = @Content)})

    // Delete user by id
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
