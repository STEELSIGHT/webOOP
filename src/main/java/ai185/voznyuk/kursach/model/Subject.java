package ai185.voznyuk.kursach.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subject_id")
    private int id;
    @Size(min = 1,max = 100,message = "Названия предмета от 1 до 100 символов!")
    private String nameSubject;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "teacher_id",nullable = false)
    @JsonIgnore
    private Teacher teacher;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "group_id",nullable = false)
    @JsonIgnore
    private Group group;
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<TeacherPost> listTeacherPost;
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<HomeWork> listHomeWork;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Subject() {
    }
    public Subject(int id) {
        this.id=id;
    }

    public List<TeacherPost> getListTeacherPost() {
        return listTeacherPost;
    }

    public void setListTeacherPost(List<TeacherPost> listTeacherPost) {
        this.listTeacherPost = listTeacherPost;
    }

    public List<HomeWork> getListHomeWork() {
        return listHomeWork;
    }

    public void setListHomeWork(List<HomeWork> listHomeWork) {
        this.listHomeWork = listHomeWork;
    }
}
