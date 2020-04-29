package ai185.voznyuk.kursach.details;

import ai185.voznyuk.kursach.model.Student;
import ai185.voznyuk.kursach.model.Teacher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TeacherDetails implements org.springframework.security.core.userdetails.UserDetails {

    private String username;
    private String password;
    private boolean isActive;
    public TeacherDetails(Teacher teacher){
        this.username=teacher.getUsername();
        this.password=teacher.getPassword();
        this.isActive=teacher.isActive();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_TEACHER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
