package ai185.voznyuk.kursach.service;

import ai185.voznyuk.kursach.model.Student;
import ai185.voznyuk.kursach.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepository repository;

    @Autowired
    public void setRepository(StudentRepository repository) {
        this.repository = repository;
    }

    public void addNewStudent(Student student){

        repository.save(student);
    }
    public List<Student> getAllStudent(){
        return repository.findAll();

    }
    public void deleteStudent(int id){
        repository.deleteById(id);
    }
    public Optional<Student> findByUsername(String username){
       return repository.findByUsername(username);
    }
}
