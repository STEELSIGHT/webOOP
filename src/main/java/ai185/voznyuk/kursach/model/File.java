package ai185.voznyuk.kursach.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class File {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    @Column(name = "file_id")
    private int id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "post_id",nullable = false)
    @JsonIgnore
    private TeacherPost teacherPost;

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
    public File(String name,TeacherPost teacherPost){
        this.name=name;
        this.teacherPost=teacherPost;
    }
    public File(){}
}
