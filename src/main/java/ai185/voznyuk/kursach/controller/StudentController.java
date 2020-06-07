package ai185.voznyuk.kursach.controller;

import ai185.voznyuk.kursach.model.FileStudent;
import ai185.voznyuk.kursach.model.HomeWorkForStudent;
import ai185.voznyuk.kursach.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private HomeWorkService homeWorkService;
    @Autowired
    private AmazonClient amazonClient;
    @Autowired
    private HomeWorkForStudentService homeWorkForStudentService;
    @Autowired
    private FileStudentService fileStudentService;
    @GetMapping("/student")
    public String viewStud(Model model, Principal principal){
        model.addAttribute("allSubject",studentService.findByUsername(principal.getName()).get().getGroup().getListSubject());
        model.addAttribute("allHomeWork",studentService.findByUsername(principal.getName()).get().getAllHomeWorkForStudent());
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
    @GetMapping("/student/turn_in_work")
    public String TurnInHomeWorkIndex(@RequestParam(name = "id") int id,Model model,Principal principal){
        for(HomeWorkForStudent temp:studentService.findByUsername(principal.getName()).get().getAllHomeWorkForStudent()){
            if(temp.getHomeWork().getId()==id){
                if(temp.getSend().equals("Да")){
                    return "send";
                }
            }
        }
        return "turn_in_work";
    }
    @PostMapping("/student/turn_in_work")
    public String TurnInHomeWorkPost(@RequestParam(name = "id") int id,
                                     @RequestParam(name = "multipartFile")  MultipartFile multipartFile,
                                     @RequestParam String post,Principal principal) throws IOException, ParseException {
        LocalDate dateWithoutTime =LocalDate.now();
        HomeWorkForStudent homeWorkForStudent= new HomeWorkForStudent(post,dateWithoutTime, studentService.findByUsername(principal.getName()).get(),homeWorkService.findHomeWorkById(id));
        homeWorkForStudentService.add(homeWorkForStudent);
            if (!multipartFile.isEmpty()) {
                String filename = amazonClient.generateFileName(multipartFile.getOriginalFilename());
                File file = File.createTempFile("tmp", "tmp");
                multipartFile.transferTo(file);
                amazonClient.upload(file, filename, amazonClient.getBucketName());
                fileStudentService.save(new FileStudent(filename, homeWorkForStudent));
            }
            return "redirect:/student";
    }


}
