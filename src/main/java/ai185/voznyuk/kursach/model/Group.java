package ai185.voznyuk.kursach.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity

@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id")
    private int group_id;
    @Pattern(regexp = "(\\pL){1,6}+[-]+([0-9][0-9][0-9])",message = "Имя группы должно быть в формате \"Аббревиатура(1-6 символов)-номер группы(3 цыфры)\"")
    private String nameGroup;
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Student> students;
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Subject> listSubject;

    public int getGroup_id() {

        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Group() {
    }

    public List<Subject> getListSubject() {
        return listSubject;
    }

    public void setListSubject(List<Subject> listSubject) {
        this.listSubject = listSubject;
    }


}
