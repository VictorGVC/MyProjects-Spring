package com.victorgvc.multilanguagespring.service.implementation;

import java.util.List;
import java.util.Optional;

import com.victorgvc.multilanguagespring.model.Item;
import com.victorgvc.multilanguagespring.model.Project;
import com.victorgvc.multilanguagespring.model.User;
import com.victorgvc.multilanguagespring.repository.ItemRepository;
import com.victorgvc.multilanguagespring.repository.ProjectRepository;
import com.victorgvc.multilanguagespring.repository.UserRepository;
import com.victorgvc.multilanguagespring.service.ProjectService;
import com.victorgvc.multilanguagespring.utils.Validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImp implements ProjectService {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    private ItemRepository itemRepository;

    @Autowired
    public ProjectServiceImp(ProjectRepository projectRepository, UserRepository userRepository, ItemRepository itemRepository) {
        super();
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public ResponseEntity<?> save(Project project, String authorization) {
        try {
            Validations.notExists(project.getName(), "Empty name");
            Validations.notExists(project.getUser(), "Invalid user");
            Validations.notOwner(project.getUser(), authorization, "Unauthorized");

            Optional<User> userFromDB = userRepository.findById(project.getUser().getId());
            if(!userFromDB.isPresent())
                throw new UsernameNotFoundException("Invalid user");
            
            if(project.getId() != null)  
            {
                Optional<Project> projectFromDB = projectRepository.findById(project.getId());
                User userFromProject = projectFromDB.get().getUser();
                Validations.notOwner(userFromProject, authorization, "Unauthorized");
                project.setUser(userFromProject);
            }
                
            projectRepository.save(project);

            return new ResponseEntity<String>("Project saved!", HttpStatus.valueOf(200));

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(400));
        }
    }

    @Override
    public List<Project> get() throws Exception {
        try {
            return projectRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Optional<Project> getById(int id) throws Exception {
        try {
            return projectRepository.findById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try {
            projectRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void addItem(int projectId, int itemId, String authorization) throws Exception {
        try {
            Optional<Project> project = projectRepository.findById(projectId);
            Optional<Item> item = itemRepository.findById(itemId);

            Validations.notPresent(project, "Invalid project id");
            Validations.notPresent(item, "Invalid item id");
            Validations.notOwner(project.get().getUser(), authorization, "Unauthorized");

            item.get().getProjects().add(project.get());

            itemRepository.save(item.get());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void removeItem(int projectId, int itemId, String authorization) throws Exception {
        // TODO Auto-generated method stub
        
    }
    
}
