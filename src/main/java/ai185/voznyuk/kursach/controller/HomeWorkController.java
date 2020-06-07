package ai185.voznyuk.kursach.controller;

import ai185.voznyuk.kursach.model.HomeWork;
import ai185.voznyuk.kursach.service.HomeWorkService;
import ai185.voznyuk.kursach.service.SubjectService;
import ai185.voznyuk.kursach.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Controller
public class HomeWorkController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private HomeWorkService homeWorkService;
    @GetMapping("/teacher/home_work")
    public String viewTeacherHomeWork(Principal principal, Model model){
        model.addAttribute("allSubject",teacherService.findByUsername(principal.getName()).get().getListSubject());
        return "teacher_home_work";
    }
    @GetMapping("/teacher/all_homework")
    public String viewAllHomeWorkForSubject(Model model, @RequestParam(name = "id") int id){
        model.addAttribute("allHomeWork",subjectService.findSubjectById(id).getListHomeWork());
        model.addAttribute("thisSubject",subjectService.findSubjectById(id));
        model.addAttribute("homeWork",new HomeWork());
        return "all_home_work_for_teacher";
    }
    @PostMapping("/teacher/add_homework")
    public String addNewHomeWork(@RequestParam(name = "id") int id, @ModelAttribute HomeWork homeWork, BindingResult result, RedirectAttributes redirectAttributes){
        homeWork.setSubject(subjectService.findSubjectById(id));
        Pattern pattern = Pattern.compile("\\d{2}-\\d{2}-\\d{4}$");
        Matcher matcher = pattern.matcher(homeWork.getCloseDate());
        if(!matcher.matches()){
            redirectAttributes.addFlashAttribute("errorFormat","Неверный формат даты! - dd-mm-yyyy");
            return "redirect:/teacher/all_homework?id="+id;
        }
        if (homeWork.getMaxMark()<=0||homeWork.getMaxMark()>100){
            redirectAttributes.addFlashAttribute("errorMaxMark","Максимальная оценка должна быть от 0 до 100");
            return "redirect:/teacher/all_homework?id="+id;
        }
        if(homeWork.getNameHomeWork().length()==0||homeWork.getNameHomeWork().length()>100){
            redirectAttributes.addFlashAttribute("errorName","Имя должно быть от 1 до 100 символов");
            return "redirect:/teacher/all_homework?id="+id;
        }
        homeWorkService.addNewHomeWork(homeWork);
        return "redirect:/teacher/home_work";
    }
    @PostMapping("/teacher/edit_homework")
    public String editHomeWork(@RequestParam int maxMark, @RequestParam String closeDate,@RequestParam String nameHomeWork,@RequestParam String descHomeWork,@RequestParam int homeWorkId, @RequestParam(name = "id") int id,RedirectAttributes redirectAttributes){
        HomeWork homeWork = new HomeWork();
        homeWork.setSubject(subjectService.findSubjectById(id));
        homeWork.setCloseDate(closeDate);
        homeWork.setDescHomeWork(descHomeWork);
        homeWork.setId(homeWorkId);
        homeWork.setMaxMark(maxMark);
        homeWork.setNameHomeWork(nameHomeWork);
        Pattern pattern = Pattern.compile("\\d{2}-\\d{2}-\\d{4}$");
        Matcher matcher = pattern.matcher(closeDate);
        if(!matcher.matches()){
            redirectAttributes.addFlashAttribute("errorFormat","Неверный формат даты! - dd-mm-yyyy");
            return "redirect:/teacher/all_homework?id="+id;
        }
        if (homeWork.getMaxMark()<=0||homeWork.getMaxMark()>100){
            redirectAttributes.addFlashAttribute("errorMaxMark","Максимальная оценка должна быть от 0 до 100");
            return "redirect:/teacher/all_homework?id="+id;
        }
        if(homeWork.getNameHomeWork().length()==0||homeWork.getNameHomeWork().length()>100){
            redirectAttributes.addFlashAttribute("errorName","Имя должно быть от 1 до 100 символов");
            return "redirect:/teacher/all_homework?id="+id;
        }
        homeWorkService.addNewHomeWork(homeWork);
        return "redirect:/teacher/home_work";
    }
    @GetMapping("/teacher/delete_homework")
    public String deleteHomeWork(@RequestParam(name = "id") int id){
        homeWorkService.deleteById(id);
        return "redirect:/teacher/home_work";
    }

}
