package ai185.voznyuk.kursach.controller;

import ai185.voznyuk.kursach.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @GetMapping("/teacher")
    public String viewTeacher(){
        return "teacher";
    }

}
