package ai185.voznyuk.kursach.repository;

import ai185.voznyuk.kursach.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher,Integer> {
    Optional<Teacher> findByUsername(String username);
}
