package com.victorgvc.multilanguagespring.service.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.victorgvc.multilanguagespring.model.User;
import com.victorgvc.multilanguagespring.repository.UserRepository;
import com.victorgvc.multilanguagespring.service.UserService;
import com.victorgvc.multilanguagespring.utils.Validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    private UserRepository repository;

    @Autowired
    public UserServiceImp(UserRepository repository) {
        super();
        this.repository = repository;
    }

    @Transactional
    @Override
    public ResponseEntity<?> save(User user, String authorization) {
        try {
            Validations.notExists(user.getUsername(), "Empty username");
            Validations.notExists(user.getPassword(), "Empty password");
            Validations.notExists(user.getConfirmPassword(), "Invalid password confirmation");
            Validations.notEquals(user.getPassword(), user.getConfirmPassword(),
                    "Password confirmation does not match");

            User userFromDB = repository.findUserByUsername(user.getUsername());

            if (user.getId() == null) {
                Validations.exists(userFromDB, "User already exists");
            } else {
                Validations.notOwner(userFromDB, authorization, "Unauthorized");
            }
            
            user.setPassword(encryptPassword(user.getPassword()));
            user.setConfirmPassword("");

            repository.save(user);

            return new ResponseEntity<String>("User saved!", HttpStatus.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
        }
    }

    @Override
    public List<User> get() throws Exception {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Optional<User> getById(int id) throws Exception {
        try {
            return repository.findById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void delete(int id) throws Exception{
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw e;
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
        if (user == null)
            throw new UsernameNotFoundException("User not found");

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        if (user.getAdmin())
            authorities.add(new SimpleGrantedAuthority("ADMIN"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

}
