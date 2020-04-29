package ai185.voznyuk.kursach.service;

import ai185.voznyuk.kursach.model.Subject;
import ai185.voznyuk.kursach.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    private SubjectRepository subjectRepository;
    @Autowired
    public void setSubjectRepository(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }
    public void addNewSubject(Subject subject){
        subjectRepository.save(subject);
    }
    public void deleteSubject(Subject subject){
        subjectRepository.delete(subject);
    }
    public List<Subject> findAllSubject(){
       return subjectRepository.findAll();
    }
    public Subject findSubjectById(int id){
       return subjectRepository.findById(id).get();
    }
    public void deleteSubjectById(int id){
        subjectRepository.deleteById(id);
    }
}
