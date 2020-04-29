package ai185.voznyuk.kursach.repository;

import ai185.voznyuk.kursach.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Integer> {
    Optional<Student> findByUsername(String username);
}
