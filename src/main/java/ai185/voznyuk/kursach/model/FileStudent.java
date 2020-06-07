package ai185.voznyuk.kursach.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class FileStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "filestudent_id")
    private int id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "homeworkforstudent_id",nullable = false)
    @JsonIgnore
    private HomeWorkForStudent homeWorkForStudent;

    public FileStudent(String name, HomeWorkForStudent homeWorkForStudent) {
        this.name = name;
        this.homeWorkForStudent = homeWorkForStudent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HomeWorkForStudent getHomeWorkForStudent() {
        return homeWorkForStudent;
    }

    public FileStudent() {
    }

    public void setHomeWorkForStudent(HomeWorkForStudent homeWorkForStudent) {
        this.homeWorkForStudent = homeWorkForStudent;
    }
}
