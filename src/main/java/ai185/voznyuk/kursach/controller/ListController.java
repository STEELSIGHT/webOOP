package ai185.voznyuk.kursach.controller;

import ai185.voznyuk.kursach.model.Subject;
import ai185.voznyuk.kursach.service.GroupService;
import ai185.voznyuk.kursach.service.SubjectService;
import ai185.voznyuk.kursach.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ListController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SubjectService subjectService;
    @GetMapping("/department/list")
    public String getViewList(Model model){
        model.addAttribute("groups",groupService.getAllGroups());
        return "list";
    }
    @GetMapping("/department/edit_list")
    public String editListForGroup(@RequestParam(value = "id") int id,Model model){
        model.addAttribute("thisGroup",groupService.findGroupById(id).get());
        model.addAttribute("listSubject",subjectService.findAllSubject());
        model.addAttribute("subject",new Subject());
        model.addAttribute("listTeacher",teacherService.getAllTeacher());
        return "edit_list";
    }
    @PostMapping("/department/add_list")
    public String saveEditSubject(@RequestParam(value = "id") int id, @ModelAttribute Subject subject, Model model){
        if(subject.getNameSubject().length()<1||subject.getNameSubject().length()>100){
            model.addAttribute("error","Названия предмета от 1 до 100 символов!");
            model.addAttribute("thisGroup",groupService.findGroupById(id).get());
            model.addAttribute("listSubject",subjectService.findAllSubject());
            model.addAttribute("subject",new Subject());
            model.addAttribute("listTeacher",teacherService.getAllTeacher());
            return "edit_list";
        }
        subject.setGroup(groupService.findGroupById(id).get());
        subjectService.addNewSubject(subject);
        return "redirect:/department/edit_list?id="+id;
    }
    @PostMapping("/department/edit_list")
    public String updateGroupSubject(@RequestParam(value = "id") int id, @RequestParam String nameSubject, @RequestParam int teacherId, @RequestParam int subjectId,Model model){
        if(nameSubject.length()<1||nameSubject.length()>100){
            model.addAttribute("error","Названия предмета от 1 до 100 символов!");
            model.addAttribute("thisGroup",groupService.findGroupById(id).get());
            model.addAttribute("listSubject",subjectService.findAllSubject());
            model.addAttribute("subject",new Subject());
            model.addAttribute("listTeacher",teacherService.getAllTeacher());
            return "edit_list";
        }
        Subject subject = subjectService.findSubjectById(subjectId);
        subject.setId(subjectId);
        subject.setGroup(groupService.findGroupById(id).get());
        subject.setTeacher(teacherService.findTeacherById(teacherId));
        subject.setNameSubject(nameSubject);
        subjectService.addNewSubject(subject);
        return "redirect:/department/edit_list?id="+id;
    }
    @GetMapping("/department/delete_subject")
    public String deleteSubject(@RequestParam (name = "id") int id ){
        int groupId=subjectService.findSubjectById(id).getGroup().getGroup_id();
        subjectService.deleteSubjectById(id);
        return "redirect:/department/edit_list?id="+groupId;
    }
}
