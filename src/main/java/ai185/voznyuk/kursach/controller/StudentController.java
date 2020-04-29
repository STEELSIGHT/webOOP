package ai185.voznyuk.kursach.controller;

import ai185.voznyuk.kursach.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class StudentController {
    @Autowired
    StudentService studentService;
    @GetMapping("/student")
    public String viewStud(Model model, Principal principal){
        model.addAttribute("name",studentService.findByUsername(principal.getName()).get().getGroup().getNameGroup());
        return "student";
    }
}
