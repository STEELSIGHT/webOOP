package ai185.voznyuk.kursach.service;

import ai185.voznyuk.kursach.model.HomeWorkForStudent;
import ai185.voznyuk.kursach.repository.HomeWorkForStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeWorkForStudentService {
    @Autowired
    private HomeWorkForStudentRepository repository;
    public void add(HomeWorkForStudent homeWorkForStudent){
        repository.save(homeWorkForStudent);
    }
    public HomeWorkForStudent findById(int id){
        return repository.findById(id).get();
    }
}
