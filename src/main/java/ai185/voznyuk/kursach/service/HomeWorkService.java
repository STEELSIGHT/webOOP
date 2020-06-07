package ai185.voznyuk.kursach.service;

import ai185.voznyuk.kursach.model.HomeWork;
import ai185.voznyuk.kursach.repository.HomeWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HomeWorkService {
    @Autowired
    private HomeWorkRepository repository;

    public void addNewHomeWork(HomeWork homeWork){
        repository.save(homeWork);
    }

    public void deleteById(int id){
        repository.deleteById(id);
    }
    public HomeWork findHomeWorkById(int id){
        return repository.findById(id).get();
    }
}
