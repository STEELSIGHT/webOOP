package ai185.voznyuk.kursach.repository;

import ai185.voznyuk.kursach.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject,Integer> {
}
