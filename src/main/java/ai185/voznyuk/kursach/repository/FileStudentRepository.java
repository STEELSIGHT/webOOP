package ai185.voznyuk.kursach.repository;

import ai185.voznyuk.kursach.model.FileStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface FileStudentRepository  extends JpaRepository<FileStudent,Integer> {
}
