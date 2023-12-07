package com.example.cda.services;

import com.example.cda.modeles.Space;
import com.example.cda.modeles.SubSubject;
import com.example.cda.modeles.Subject;
import com.example.cda.modeles.User;

public interface SubjectService {
    Subject save(Subject subject);
    Subject get(Long id);
    Iterable<Subject> getAllSubjectBySpace(Space space);
    void delete(Subject subject);
    Subject update(Subject subject, String name);
    boolean subSubjectExistSubject(Subject subject, Long idSubSubject);
}
