package ai185.voznyuk.kursach.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibm.icu.text.Transliterator;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Teacher {
    @Column(name = "teacher_id")
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private int id;
    @Size(min = 2,max = 35,message = "Имя не может быть больше 35 или меньше чем 2 символа")
    private String firstName;
    @Size(min = 2,max = 35,message = "Отчество не может быть больше 35 или меньше чем 2 символа")
    private String lastName;
    @Size(min = 2,max = 35, message = "Фамилия не может быть больше 35 или меньше чем 2 символа")
    private String patronymic;
    @Size(min = 2,max = 50, message = "Должность не может быть больше 50 или меньше чем 2 символа")
    private String position;
    @Size(min = 6,max = 25, message = "Пароль не может быть больше 25 или меньше чем 6 символов")
    @Pattern(regexp = "[0-9a-zA-Z!@#$%^&*()_+=?.<>,-]{0,}",message = "Пароль может содежать: латинские буквы; цифры; спец символы( !@#$%^&*()_+=?.<>,- )")
    private String password;
    @Pattern(regexp = "[0-9a-zA-Z!@#$%^&*()_+=?.<>,-]{0,}",message = "Имя пользователя может содежать: латинские буквы; цифры; спец символы( !@#$%^&*()_+=?.<>,- )")
    private String username;
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Subject>  listSubject;
    private boolean isActive;
    private String role;
    @Transient
    private boolean generatedUsername;

    public boolean isGeneratedUsername() {
        return generatedUsername;
    }
    public String selectTeacher(){
        return lastName+" "+firstName.toUpperCase().charAt(0)+". "+patronymic.toUpperCase().charAt(0)+". @"+username;
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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Teacher() {
        this.setGeneratedUsername(false);
    }

    public List<Subject> getListSubject() {
        return listSubject;
    }

    public void setListSubject(List<Subject> listSubject) {
        this.listSubject = listSubject;
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
