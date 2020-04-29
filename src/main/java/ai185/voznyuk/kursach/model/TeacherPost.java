package ai185.voznyuk.kursach.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class TeacherPost {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private int id;
    @Column(columnDefinition = "TEXT")
    private String text;
    @OneToMany(mappedBy = "teacherPost", cascade = CascadeType.ALL)
    private List<File> listFile;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "subject_id",nullable = false)
    @JsonIgnore
    private Subject subject;

    public TeacherPost(String text,Subject subject){
        this.text=text;
        this.subject=subject;
    }
    public TeacherPost(){}

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

    public List<File> getListFile() {
        return listFile;
    }

    public void setListFile(List<File> listFile) {
        this.listFile = listFile;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
