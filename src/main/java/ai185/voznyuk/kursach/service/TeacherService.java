package ai185.voznyuk.kursach.service;

import ai185.voznyuk.kursach.model.Teacher;
import ai185.voznyuk.kursach.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    private TeacherRepository teacherRepository;
    @Autowired
    public void setTeacherRepository(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }
    public List<Teacher> getAllTeacher(){
        return teacherRepository.findAll();
    }
    public void addNewTeacher(Teacher teacher){
        teacherRepository.save(teacher);
    }
    public Teacher findTeacherById(int id){
        return teacherRepository.findById(id).get();
    }
    public void teacherDelete(int id){
        teacherRepository.deleteById(id);
    }
    public Optional<Teacher> findByUsername(String username){
        return teacherRepository.findByUsername(username);
    }

}
