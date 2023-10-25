package com.example.cda.services;

import com.example.cda.modeles.Space;
import com.example.cda.modeles.SubSubject;
import com.example.cda.modeles.Subject;

import java.util.List;

public interface SubSubjectService {

    SubSubject save(SubSubject subSubject);
    SubSubject get(Long id);
    List<SubSubject> getAllSubSubjectBySubject(Subject subject);
    void delete(Long id);
    SubSubject update(SubSubject subSubject, SubSubject newSubSubject);
}
