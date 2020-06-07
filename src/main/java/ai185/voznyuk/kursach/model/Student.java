package ai185.voznyuk.kursach.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibm.icu.text.Transliterator;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    @Column(name = "student_id")
    private int id;
    @Size(min = 2,max = 35,message = "Имя не может быть больше 35 или меньше чем 2 символа")
    private String firstName;
    @Size(min = 2,max = 35,message = "Отчество не может быть больше 35 или меньше чем 2 символа")
    private String lastName;
    @Size(min = 2,max = 35, message = "Фамилия не может быть больше 35 или меньше чем 2 символа")
    private String patronymic;
    @Size(min = 6,max = 25, message = "Пароль не может быть больше 25 или меньше чем 6 символов")
    @Pattern(regexp = "[0-9a-zA-Z!@#$%^&*()_+=?.<>,-]{0,}",message = "Пароль может содежать: латинские буквы; цифры; спец символы( !@#$%^&*()_+=?.<>,- )")
    private String password;
    @Pattern(regexp = "[0-9a-zA-Z!@#$%^&*()_+=?.<>,-]{0,}",message = "Имя пользователя может содежать: латинские буквы; цифры; спец символы( !@#$%^&*()_+=?.<>,- )")
    private String username;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "group_id",nullable = false)
    @JsonIgnore
    private Group group;
    private boolean isActive;
    @Transient
    private boolean generatedUsername;
    private String role;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<HomeWorkForStudent> allHomeWorkForStudent;

    public Student() {
        this.setGeneratedUsername(false);
    }

    public List<HomeWorkForStudent> getAllHomeWorkForStudent() {
        return allHomeWorkForStudent;
    }

    public void setAllHomeWorkForStudent(List<HomeWorkForStudent> allHomeWorkForStudent) {
        this.allHomeWorkForStudent = allHomeWorkForStudent;
    }

    public boolean isGeneratedUsername() {
        return generatedUsername;
    }

    public void setGeneratedUsername(boolean generatedUsername) {
        this.generatedUsername = generatedUsername;
    }

    public String generatedUsername(String username){
       final String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";
        Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        String result = toLatinTrans.transliterate(username);
        return result;
    }

    public boolean equalsUsername(Student student) {
       return this.getUsername()==student.getUsername();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
