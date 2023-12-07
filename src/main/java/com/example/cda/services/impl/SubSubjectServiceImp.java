package com.example.cda.services.impl;

import com.example.cda.modeles.Event;
import com.example.cda.modeles.SubSubject;
import com.example.cda.modeles.Subject;
import com.example.cda.repositorys.SubSubjectRepository;
import com.example.cda.services.SubSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubSubjectServiceImp implements SubSubjectService {

    @Autowired
    SubSubjectRepository subSubjectRepository;

    @Override
    public SubSubject save(SubSubject subSubject) {
        return subSubjectRepository.save(subSubject);
    }

    @Override
    public SubSubject get(Long id) {
        return subSubjectRepository.findById(id).orElse(null);
    }

    @Override
    public List<SubSubject> getAllSubSubjectBySubject(Subject subject) {

       return subject.getSubSubjects();
    }

    @Override
    public void delete(SubSubject subSubject) {
        subSubjectRepository.delete(subSubject);
    }

    @Override
    public SubSubject update(SubSubject subSubject,SubSubject newSubSubject) {
        subSubject.setTitle(newSubSubject.getTitle());
        subSubject.setMedicalSpecialties(newSubSubject.getMedicalSpecialties());
        subSubject.setSubject(newSubSubject.getSubject());
        return subSubjectRepository.save(subSubject);
    }
}
