package ai185.voznyuk.kursach.controller;

import ai185.voznyuk.kursach.model.HomeWorkForStudent;
import ai185.voznyuk.kursach.model.Subject;
import ai185.voznyuk.kursach.service.HomeWorkForStudentService;
import ai185.voznyuk.kursach.service.HomeWorkService;
import ai185.voznyuk.kursach.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Controller
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private HomeWorkService homeWorkService;
    @Autowired
    private HomeWorkForStudentService homeWorkForStudentService;
    @GetMapping("/teacher")
    public String viewTeacher(Model model, Principal principal){
        model.addAttribute("allSubject",teacherService.findByUsername(principal.getName()).get().getListSubject());
        return "teacher";
    }
    @GetMapping("/teacher/check_work")
    public String checkWorkIndex(@RequestParam (name = "id") int id,Model model,Principal principal){
        model.addAttribute("homeWorkForId",homeWorkService.findHomeWorkById(id));
        model.addAttribute("allHomeWorkForId",homeWorkService.findHomeWorkById(id).getHomeWorkForStudentList());
        return "check_work";
    }
    @GetMapping("/teacher/check_work_for_student")
    public String checkHomeWorkForStudentIndex(@RequestParam (name = "id") int id,Model model){
        model.addAttribute("thisHomeWork",homeWorkForStudentService.findById(id));
        return "check_work_for_student";
    }
    @PostMapping("/teacher/check_work_for_student")
    public String estimate(@RequestParam (name = "id") int id, @RequestParam float mark, Model model, RedirectAttributes redirectAttributes){
        HomeWorkForStudent temp = homeWorkForStudentService.findById(id);
        temp.setMark(mark);
        if(mark>homeWorkForStudentService.findById(id).getHomeWork().getMaxMark()||mark<0){
            redirectAttributes.addFlashAttribute("error","Оценка не может быть больше максимальной или меньше 0!");
            return "redirect:/teacher/check_work_for_student?id="+id;
        }
        homeWorkForStudentService.add(temp);
        return "redirect:/teacher";
    }

}
