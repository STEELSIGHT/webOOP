package ai185.voznyuk.kursach.service;

import ai185.voznyuk.kursach.details.StudentDetails;
import ai185.voznyuk.kursach.details.TeacherDetails;
import ai185.voznyuk.kursach.model.Student;
import ai185.voznyuk.kursach.model.Teacher;
import ai185.voznyuk.kursach.repository.StudentRepository;
import ai185.voznyuk.kursach.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
     private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Student> user = studentRepository.findByUsername(s);
        Optional<Teacher> user2 = teacherRepository.findByUsername(s);
        if(!user.isPresent()&&!user2.isPresent()){
            throw new UsernameNotFoundException("User "+s+" not found!");
        }
        if(user.isPresent()){
            return user.map(StudentDetails::new).get();
        }
        if(user2.isPresent()){
            return user2.map(TeacherDetails::new).get();
        }
        return null;
    }
}
