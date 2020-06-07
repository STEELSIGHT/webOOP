package ai185.voznyuk.kursach.service;

import ai185.voznyuk.kursach.model.File;
import ai185.voznyuk.kursach.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    @Autowired
     private FileRepository fileRepository;

    public void addNewFile(File file){
        fileRepository.save(file);
    }
    public void deleteFileById(int id){
        fileRepository.deleteById(id);
    }
}
