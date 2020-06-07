package ai185.voznyuk.kursach.service;

import ai185.voznyuk.kursach.model.Group;
import ai185.voznyuk.kursach.model.Student;
import ai185.voznyuk.kursach.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
     private GroupRepository repository;
    @Autowired
    public void setRepository(GroupRepository repository) {
        this.repository = repository;
    }

    public List<Group> getAllGroups(){
        return repository.findAll();
    }
    public void addNewGroup(Group group){
        repository.save(group);
    }
    public Optional<Group> findGroupById(int id){
        return repository.findById(id);
    }
    public void deleteGroupById(int id){
        repository.deleteById(id);
    }
}
