package com.victorgvc.multilanguagespring.service.implementation;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.victorgvc.multilanguagespring.model.User;
import com.victorgvc.multilanguagespring.repository.UserRepository;
import com.victorgvc.multilanguagespring.service.UserService;
import com.victorgvc.multilanguagespring.utils.Validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImp implements UserService, UserDetailsService{

    private UserRepository repository;

    @Autowired
    public UserServiceImp(UserRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    @Transactional
    public ResponseEntity<?> save(User user) {
        try {
            Validations.notExists(user.getUsername(), "Empty username");
            Validations.notExists(user.getPassword(), "Empty password");
            Validations.notExists(user.getConfirmPassword(), "Invalid password confirmation");
            Validations.notEquals(user.getPassword(), user.getConfirmPassword(), "Password confirmation does not match");

            User userFromDB = repository.findUserByUsername(user.getUsername());
            
            if(user.getId() == null)
                Validations.exists(userFromDB, "User already exists");
            user.setPassword(encryptPassword(user.getPassword()));
            user.setConfirmPassword("");
    
            repository.save(user);

            return new ResponseEntity<>("User saved!", CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e, CREATED);
        }
    }

    @Override
    public String encryptPassword(String password) {
        String salt = BCrypt.gensalt(10);
        String passEncoded = BCrypt.hashpw(password, salt);
        return passEncoded;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findUserByUsername(username);
        if(user == null)
            throw new UsernameNotFoundException("User not found");
        
        Collection <SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        if(user.getAdmin())
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public List<User> get() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User getById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User delete(int id) {
        // TODO Auto-generated method stub
        return null;
    }

}
