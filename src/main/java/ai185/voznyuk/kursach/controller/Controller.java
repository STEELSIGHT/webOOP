package ai185.voznyuk.kursach.controller;

import ai185.voznyuk.kursach.model.Student;
import ai185.voznyuk.kursach.model.Teacher;
import ai185.voznyuk.kursach.service.StudentService;
import ai185.voznyuk.kursach.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @GetMapping("/success")
    public String redirectAfterLogin(Principal principal){
        Optional<Student> student= studentService.findByUsername(principal.getName());
        Optional<Teacher> teacher= teacherService.findByUsername(principal.getName());

        if(student.isPresent()){
            return "redirect:/student";
        }
        if (teacher.isPresent()){
            return "redirect:/teacher";
        }
        return "redirect:/department";
    }
    @GetMapping("/")
        public String indexRedirect(Principal principal){
            Optional<Student> student= studentService.findByUsername(principal.getName());
            Optional<Teacher> teacher= teacherService.findByUsername(principal.getName());
            if(student.isPresent()){
                return "redirect:/student";
            }
             else if (teacher.isPresent()){
                return "redirect:/teacher";
            }
             else if (principal.getName().equals("admin")){
                return "redirect:/department";
            }
             return "redirect:/";
        }
}
