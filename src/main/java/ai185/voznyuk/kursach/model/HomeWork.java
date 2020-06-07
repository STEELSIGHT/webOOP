package ai185.voznyuk.kursach.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class HomeWork {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "homework_id")
    private int id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "subject_id",nullable = false)
    @JsonIgnore
    private Subject subject;
    private String nameHomeWork;
    private String descHomeWork;
    private int maxMark;
    private String closeDate;
    @OneToMany(mappedBy = "homeWork", cascade = CascadeType.ALL)
    private List<HomeWorkForStudent> homeWorkForStudentList;

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public int getId() {
        return id;
    }

    public List<HomeWorkForStudent> getHomeWorkForStudentList() {
        return homeWorkForStudentList;
    }

    public void setHomeWorkForStudentList(List<HomeWorkForStudent> homeWorkForStudentList) {
        this.homeWorkForStudentList = homeWorkForStudentList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }


    public String getNameHomeWork() {
        return nameHomeWork;
    }

    public void setNameHomeWork(String nameHomeWork) {
        this.nameHomeWork = nameHomeWork;
    }

    public String getDescHomeWork() {
        return descHomeWork;
    }

    public void setDescHomeWork(String descHomeWork) {
        this.descHomeWork = descHomeWork;
    }

    public int getMaxMark() {
        return maxMark;
    }

    public void setMaxMark(int maxMark) {
        this.maxMark = maxMark;
    }
}
