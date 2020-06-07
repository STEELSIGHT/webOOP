package ai185.voznyuk.kursach.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class HomeWorkForStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "homeworkforstudent_id")
    private int id;
    @Column(columnDefinition = "TEXT")
    private String text;
    private float mark;
    private LocalDate date;
    @OneToMany(mappedBy = "homeWorkForStudent", cascade = CascadeType.ALL)
    private List<FileStudent> fileStudentList;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "student_id",nullable = false)
    @JsonIgnore
    private Student student;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "homework_id",nullable = false)
    @JsonIgnore
    private HomeWork homeWork;
    private String send;

    public HomeWorkForStudent(String text, LocalDate date, Student student, HomeWork homeWork) {
        this.text = text;
        this.date = date;
        this.student = student;
        this.homeWork = homeWork;
        this.send="Да";
    }

    public HomeWorkForStudent() {
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<FileStudent> getFileStudentList() {
        return fileStudentList;
    }

    public void setFileStudentList(List<FileStudent> fileStudentList) {
        this.fileStudentList = fileStudentList;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public HomeWork getHomeWork() {
        return homeWork;
    }

    public void setHomeWork(HomeWork homeWork) {
        this.homeWork = homeWork;
    }
}
