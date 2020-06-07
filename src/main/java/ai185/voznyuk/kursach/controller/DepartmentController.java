package ai185.voznyuk.kursach.controller;

import ai185.voznyuk.kursach.model.Student;
import ai185.voznyuk.kursach.model.Teacher;
import ai185.voznyuk.kursach.service.GroupService;
import ai185.voznyuk.kursach.service.StudentService;
import ai185.voznyuk.kursach.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class DepartmentController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @GetMapping("/department")
    public String viewDepartment(Model model){
        model.addAttribute("students",studentService.getAllStudent());
        model.addAttribute("groups",groupService.getAllGroups());
        model.addAttribute("teachers",teacherService.getAllTeacher());
        return "department";
    }
    @GetMapping("/department/add_student")
    public String viewAddStudent(Model model){
        model.addAttribute("student",new Student());
        model.addAttribute("groups",groupService.getAllGroups());
        return "add_student";
    }
    @PostMapping("department/add_student")
    public String addNewStudent(@Valid Student student,BindingResult result,Model model){
        student.setActive(true);
        student.setRole("ROLE_STUDENT");
        if (result.hasErrors()) {
            if(!student.isGeneratedUsername()&&(student.getUsername().isEmpty()||student.getUsername().length()>35||student.getUsername().length()<4)){
                model.addAttribute("errorUsername","Имя пользоватаеля не может быть больше 35 или меньше чем 4 символа");
                model.addAttribute("groups",groupService.getAllGroups());
                return "add_student";
            }
            model.addAttribute("groups",groupService.getAllGroups());
            return "add_student";
        }
        if(!student.isGeneratedUsername()&&(student.getUsername().isEmpty()||student.getUsername().length()>35||student.getUsername().length()<4)){
            model.addAttribute("errorUsername","Имя пользоватаеля не может быть больше 35 или меньше чем 4 символа");
            model.addAttribute("groups",groupService.getAllGroups());
            return "add_student";
        }
        if(student.isGeneratedUsername()) {
            for (Student student1 : studentService.getAllStudent()) {
                if (student.generatedUsername(student.getFirstName() + student.getLastName() + student.getPatronymic()).equals(student1.getUsername())) {
                    model.addAttribute("errorUsername","Такой пользователь уже существует!");
                    model.addAttribute("groups",groupService.getAllGroups());
                    return "add_student";
                }
                student.setUsername(student.generatedUsername(student.getFirstName() + student.getLastName()));
            }
            if (teacherService.getAllTeacher().isEmpty()) {
                student.setUsername(student.generatedUsername(student.getFirstName() + student.getLastName()));
            }
        }
        for(Student student1:studentService.getAllStudent()){
            if(student.getUsername().equals(student1.getUsername())){
                model.addAttribute("errorUsername","Такой пользователь уже существует!");
                model.addAttribute("groups",groupService.getAllGroups());
                return "add_student";
            }
        }
        for(Teacher teacher:teacherService.getAllTeacher()){
            if(teacher.getUsername().equals(student.getUsername())){
                model.addAttribute("errorUsername","Такой пользователь уже существует!");
                model.addAttribute("groups",groupService.getAllGroups());
                return "add_student";
            }
        }
        studentService.addNewStudent(student);
        return "redirect:/department";
    }
    @GetMapping("/department/add_teacher")
    public String viewAddTeacher(Model model){
        model.addAttribute("teacher",new Teacher());
        return "add_teacher";
    }
    @PostMapping("/department/add_teacher")
    public String addNewTeacher(@Valid Teacher teacher,BindingResult result,Model model){
        teacher.setActive(true);
        teacher.setRole("ROLE_TEACHER");
            if (result.hasErrors()) {
                if(!teacher.isGeneratedUsername()&&(teacher.getUsername().isEmpty()||teacher.getUsername().length()>35||teacher.getUsername().length()<4)){
                    model.addAttribute("errorUsername","Имя пользоватаеля не может быть больше 35 или меньше чем 4 символа");
                    return "add_teacher";
                }
                return "add_teacher";
            }
        if(!teacher.isGeneratedUsername()&&(teacher.getUsername().isEmpty()||teacher.getUsername().length()>35||teacher.getUsername().length()<4)){
            model.addAttribute("errorUsername","Имя пользоватаеля не может быть больше 35 или меньше чем 4 символа");
            return "add_teacher";
        }
        if(teacher.isGeneratedUsername()) {
            for (Teacher teacher1 : teacherService.getAllTeacher()) {
                if (teacher.generatedUsername(teacher.getFirstName() + teacher.getLastName() + teacher.getPatronymic()).equals(teacher1.getUsername())) {
                    model.addAttribute("errorUsername","Такой пользователь уже существует!");
                    return "add_teacher";
                }
                teacher.setUsername(teacher.generatedUsername(teacher.getFirstName() + teacher.getLastName()));
            }
            if (teacherService.getAllTeacher().isEmpty()) {
                teacher.setUsername(teacher.generatedUsername(teacher.getFirstName() + teacher.getLastName()));
            }
        }
        for(Teacher teacher1:teacherService.getAllTeacher()){
            if(teacher.getUsername().equals(teacher1.getUsername())){
                model.addAttribute("errorUsername","Такой пользователь уже существует!");
                return "add_teacher";
            }
        }
        for(Student student1:studentService.getAllStudent()){
            if(student1.getUsername().equals(teacher.getUsername())){
                model.addAttribute("errorUsername","Такой пользователь уже существует!");
                return "add_teacher";
            }
        }
        teacherService.addNewTeacher(teacher);
        return "redirect:/department";
    }
    @GetMapping("/department/delete_teacher")
    public String deleteTeacher(@RequestParam (name = "id") int id){
        teacherService.teacherDelete(id);
        return "redirect:/department";
    }
    @GetMapping("/department/delete_student")
    public String deleteStudent(@RequestParam (name = "id") int id){
        studentService.deleteStudent(id);
        return "redirect:/department";
    }

}
