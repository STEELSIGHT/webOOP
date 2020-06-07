package ai185.voznyuk.kursach.controller;

import ai185.voznyuk.kursach.model.Group;
import ai185.voznyuk.kursach.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class GroupController {
    @Autowired
    private GroupService service;
    @GetMapping("/department/group")
    public String viewGroup(Model model){
        model.addAttribute("groups",service.getAllGroups());
        return "group";
    }
    @GetMapping("/department/add_group")
    public String viewAddNewGroup(Model model){
        model.addAttribute("group",new Group());
        return "add_group";
    }
    @PostMapping("/department/add_group")
    public String addNewGroup(@Valid Group group, BindingResult result,Model model){
        if(result.hasErrors()){
            return "add_group";
        }
        group.setNameGroup(group.getNameGroup().toUpperCase());
        for(Group group1:service.getAllGroups()){
            if(group.getNameGroup().equals(group1.getNameGroup())){
                model.addAttribute("nameGroupToUse","Такая группа уже существует!");
                return "add_group";
            }
        }
        service.addNewGroup(group);
        return "redirect:/department/group";
    }
    @GetMapping("/department/delete_group")
    public String deleteClearGroup(@RequestParam (value = "id") int id, Model model){
        if(!service.findGroupById(id).get().getStudents().isEmpty()){
            model.addAttribute("groups",service.getAllGroups());
            model.addAttribute("error_delete_group","Группа не может быть удаленна,так как в ней есть студенты");
            return "group";
        }
        service.deleteGroupById(id);
        return "redirect:/department/group";
    }
}
