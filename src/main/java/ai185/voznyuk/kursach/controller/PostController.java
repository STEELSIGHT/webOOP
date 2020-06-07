package ai185.voznyuk.kursach.controller;

import ai185.voznyuk.kursach.model.TeacherPost;
import ai185.voznyuk.kursach.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    private FileService fileService;
    @Autowired
    private AmazonClient amazonClient;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private TeacherPostService teacherPostService;
    @GetMapping("/teacher/teacher_post")
    public String viewTeacherPost(Model model, Principal principal){
        model.addAttribute("allSubjectForTeacher",teacherService.findByUsername(principal.getName()).get().getListSubject());
        return "teacher_post";
    }
    @GetMapping("/teacher/all_post")
    public String viewAllPostForSubject(@RequestParam(name = "id") int id, Model model,Principal principal){
        List<TeacherPost> allTeacherPost = subjectService.findSubjectById(id).getListTeacherPost();
        model.addAttribute("allTeacherPost",allTeacherPost);
        model.addAttribute("listSubject",teacherService.findByUsername(principal.getName()).get().getListSubject());
        model.addAttribute("thisSubject",subjectService.findSubjectById(id));
        return "all_post";
    }
    @GetMapping("/teacher/add_post")
    public String viewAddPost(Model model,Principal principal){
        model.addAttribute("listSubject",teacherService.findByUsername(principal.getName()).get().getListSubject());
        return "add_post";
    }
    @PostMapping("/teacher/add_post")
    public String addNewPost(@RequestParam(name = "multipartFile")  MultipartFile[] multipartFiles,
                             @RequestParam String post,
                             @RequestParam int subjectId) throws IOException {
       TeacherPost teacherPost= new TeacherPost(post,subjectService.findSubjectById(subjectId));
       teacherPostService.addNewPost(teacherPost);
       for(MultipartFile multipartFile:multipartFiles) {
           if (!multipartFile.isEmpty()) {
               String filename = amazonClient.generateFileName(multipartFile.getOriginalFilename());
               File file = File.createTempFile("tmp", "tmp");
               multipartFile.transferTo(file);
               amazonClient.upload(file, filename, amazonClient.getBucketName());
               fileService.addNewFile(new ai185.voznyuk.kursach.model.File(filename, teacherPost));
           }
       }
        return "redirect:/teacher/teacher_post";
    }
    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("filename") String fileName){
        ByteArrayOutputStream byteArrayOutputStream = amazonClient.downloadFile(fileName, amazonClient.getBucketName());
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + fileName + "\"")
                .body(byteArrayOutputStream.toByteArray());
    }
    @GetMapping("/delete/{filename}/{idSubject}/{idFile}")
    public String deleteFile(@PathVariable("filename") String fileName,@PathVariable(name = "idSubject") int idSubject,@PathVariable(name = "idFile") int idFile){
        amazonClient.deleteFromBucket(amazonClient.getBucketName(),fileName);
        fileService.deleteFileById(idFile);
        return "redirect:/teacher/all_post?id="+idSubject;
    }

    @GetMapping("/teacher/delete_post/{idTeacherPost}/{idSubject}")
    public String deleteTeacherPost(@PathVariable (name = "idTeacherPost") int idTeacherPost,@PathVariable(name = "idSubject") int idSubject){
        teacherPostService.deleteTeacherById(idTeacherPost);
        return "redirect:/teacher/all_post?id="+idSubject;
    }

    @PostMapping("/teacher/edit_post/{id}")
    public String editTeacherPost(@PathVariable(name = "id") int idSubject,
                                  @RequestParam String post,
                                  @RequestParam int subjectId,
                                  @RequestParam int idTeacherPost,
                                  @RequestParam(name = "file") MultipartFile file) throws MaxUploadSizeExceededException, IOException {
        TeacherPost teacherPost = teacherPostService.findTeacherPostById(idTeacherPost);
        teacherPost.setSubject(subjectService.findSubjectById(subjectId));
        teacherPost.setText(post);
        teacherPostService.addNewPost(teacherPost);
        if(!file.isEmpty()){

                if (teacherPost.getListFile().size() <= 4) {
                    String filename = amazonClient.generateFileName(file.getOriginalFilename());
                    File file1 = File.createTempFile("tmp", "tmp");
                    file.transferTo(file1);
                    amazonClient.upload(file1, filename, amazonClient.getBucketName());
                    fileService.addNewFile(new ai185.voznyuk.kursach.model.File(filename, teacherPost));

                }
            }
        return "redirect:/teacher/all_post?id="+idSubject;
    }

}
