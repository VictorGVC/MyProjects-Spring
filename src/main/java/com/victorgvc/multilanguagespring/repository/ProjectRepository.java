package com.victorgvc.multilanguagespring.repository;

import com.victorgvc.multilanguagespring.model.Project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer>{
    
}
