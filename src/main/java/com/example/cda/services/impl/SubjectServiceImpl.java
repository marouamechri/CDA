package com.example.cda.services.impl;

import com.example.cda.modeles.Space;
import com.example.cda.modeles.SubSubject;
import com.example.cda.modeles.Subject;
import com.example.cda.modeles.User;
import com.example.cda.repositorys.SubjectRepository;
import com.example.cda.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    SubjectRepository subjectRepository;
    @Override
    public Subject save(Subject subject) {

        return subjectRepository.save(subject);


    }

    @Override
    public Subject get(Long id) {

        return subjectRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Subject> getAllSubjectBySpace(Space space) {

        return space.getSubjects();
    }

    @Override
    public void delete(Long id) {
        Subject subjectExist = subjectRepository.findById(id).orElse(null);
        if(subjectExist!= null){
            subjectRepository.delete( subjectExist);
        }

    }

    @Override
    public Subject update(Subject subject, String title) {

        subject.setTitle(title);
        return subjectRepository.save(subject);
    }

    @Override
    public boolean subSubjectExistSubject(Subject subject, Long idSubSubject) {
        List<SubSubject> subSubjects = subject.getSubSubjects();
        boolean exist = false;
        if(subSubjects!= null){
            for (SubSubject s: subSubjects) {
                if(s.getId() == idSubSubject){
                    exist = true;
                }
            }
        }
        return exist;
    }
}
