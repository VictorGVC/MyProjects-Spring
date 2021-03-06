package com.victorgvc.multilanguagespring.service;

import java.util.List;
import java.util.Optional;

import com.victorgvc.multilanguagespring.model.Project;

import org.springframework.http.ResponseEntity;

public interface ProjectService {
    ResponseEntity<?> save(Project project, String authorization);
    List<Project> get() throws Exception;
    Optional<Project> getById(int id) throws Exception;
    void delete(int id) throws Exception;
    void addItem(int projectId, int itemId, String authorization) throws Exception;
    void removeItem(int projectId, int itemId, String authorization) throws Exception;
}
