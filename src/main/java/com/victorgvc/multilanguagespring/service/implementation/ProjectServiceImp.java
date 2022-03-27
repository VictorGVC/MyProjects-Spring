package com.victorgvc.multilanguagespring.service.implementation;

import java.util.List;
import java.util.Optional;

import com.victorgvc.multilanguagespring.model.Project;
import com.victorgvc.multilanguagespring.model.User;
import com.victorgvc.multilanguagespring.repository.ProjectRepository;
import com.victorgvc.multilanguagespring.repository.UserRepository;
import com.victorgvc.multilanguagespring.service.ProjectService;
import com.victorgvc.multilanguagespring.utils.Validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ProjectServiceImp implements ProjectService {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    @Autowired
    public ProjectServiceImp(ProjectRepository projectRepository, UserRepository userRepository) {
        super();
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> save(Project project) {
        try {
            Validations.notExists(project.getName(), "Empty name");
            Validations.notExists(project.getUser(), "Invalid user");

            Optional<User> userFromDB = userRepository.findById(project.getUser().getId());
            if(!userFromDB.isPresent())
                throw new UsernameNotFoundException("Invalid user");

            projectRepository.save(project);

            return new ResponseEntity<String>("Project saved!", HttpStatus.valueOf(200));

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
        }
    }

    @Override
    public List<Project> get() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Project> getById(int id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(int id) throws Exception {
        // TODO Auto-generated method stub
        
    }
    
}
