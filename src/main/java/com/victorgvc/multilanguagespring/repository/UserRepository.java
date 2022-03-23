package com.victorgvc.multilanguagespring.repository;

import com.victorgvc.multilanguagespring.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
    
    User findUserByUsername(String username);
}
