package ai185.voznyuk.kursach.service;

import ai185.voznyuk.kursach.model.FileStudent;
import ai185.voznyuk.kursach.repository.FileStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileStudentService {
    @Autowired
    private FileStudentRepository repository;

    public void save(FileStudent fileStudent){
        repository.save(fileStudent);
    }
}
