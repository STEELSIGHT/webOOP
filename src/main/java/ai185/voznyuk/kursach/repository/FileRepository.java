package ai185.voznyuk.kursach.repository;

import ai185.voznyuk.kursach.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File,Integer> {
}
