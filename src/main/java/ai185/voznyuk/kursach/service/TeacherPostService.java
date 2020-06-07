package ai185.voznyuk.kursach.service;

import ai185.voznyuk.kursach.model.TeacherPost;
import ai185.voznyuk.kursach.repository.TeacherPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherPostService {
    @Autowired
    private TeacherPostRepository teacherPostRepository;

    public void addNewPost(TeacherPost post){
        teacherPostRepository.save(post);
    }

    public void deleteTeacherById(int id){
        teacherPostRepository.deleteById(id);
    }
    public TeacherPost findTeacherPostById(int id){
        return teacherPostRepository.findById(id).get();
    }
}
