package ai185.voznyuk.kursach.controller;

import ai185.voznyuk.kursach.service.StudentService;
import ai185.voznyuk.kursach.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    SubjectService subjectService;
    @GetMapping("/student")
    public String viewStud(Model model, Principal principal){
        model.addAttribute("name",studentService.findByUsername(principal.getName()).get().getGroup().getNameGroup());
        return "student";
    }
    @GetMapping("/student/all_subject_post")
    public String viewAllSubjectPost(Principal principal,Model model){
        model.addAttribute("allSubjectForStudent",studentService.findByUsername(principal.getName()).get().getGroup().getListSubject());
        return "all_subject_post";
    }
    @GetMapping("/student/all_post")
    public String allPostForSubject(@RequestParam(name = "id") int id, Model model){
        model.addAttribute("allPostForSubject",subjectService.findSubjectById(id).getListTeacherPost());
        model.addAttribute("thisSubject",subjectService.findSubjectById(id));
        return "all_post_for_student";
    }
}
