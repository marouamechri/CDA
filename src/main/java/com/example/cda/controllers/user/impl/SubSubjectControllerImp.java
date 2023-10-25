package com.example.cda.controllers.user.impl;

import com.example.cda.controllers.user.SubSubjectController;
import com.example.cda.dtos.SubSubjectDto;
import com.example.cda.exceptions.*;
import com.example.cda.modeles.*;
import com.example.cda.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;

@RestController
public class SubSubjectControllerImp implements SubSubjectController {
    @Autowired
    SpaceService spaceService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    UserService userService;
    @Autowired
    SubSubjectService subSubjectService;
    @Autowired
    MedicalSpecialtiesService medicalService;

    @Override
    public ResponseEntity<SubSubject> save(@RequestBody SubSubjectDto dto, Principal principal, @PathVariable Long idSpace,@PathVariable Long idSubject) throws SubSubjectExistException, URISyntaxException, SpaceNotFoundException, SubjectNotFoundException {
        //System.out.println("debut");

        Space space= spaceService.get(idSpace);
        Subject subject = subjectService.get(idSubject);
        User user = (User) userService.loadUserByUsername(principal.getName());
        MedicalSpecialties medicalSpecialties = medicalService.get(dto.getMedicalSpecialties());

        boolean subSubjectExist = false;

        if((space!=null) && (user.getId() == space.getUser().getId())
                &&(spaceService.subjectExistSpace(space, idSubject)&&(medicalSpecialties!=null))){

            List<SubSubject> subSubjects = subSubjectService.getAllSubSubjectBySubject(subject);
            System.out.println(subSubjects==null);
            if(subSubjects!=null) {
                for (SubSubject s : subSubjects) {
                    if (s.getTitle().equals(dto.getTitle())) {
                        subSubjectExist = true;
                    }
                }
            }
            if(!subSubjectExist){
                SubSubject subSubject = new SubSubject();
                subSubject.setTitle(dto.getTitle());
                subSubject.setSubject(subject);
                subSubject.setMedicalSpecialties(medicalSpecialties);
                SubSubject result = subSubjectService.save(subSubject);
                return ResponseEntity.status(201).body(result);
            }else {
                throw new SubSubjectExistException();
            }

        }else
            return ResponseEntity.status(403).body(null);

    }

    @Override
    public ResponseEntity<SubSubject> get(@PathVariable Long idSpace, Principal principal,@PathVariable Long idSubject,@PathVariable Long idSubSubject) throws URISyntaxException, SubjectNotFoundException, SpaceNotFoundException, SubSubjectNotFoundException {
        Space space= spaceService.get(idSpace);
        Subject subject = subjectService.get(idSubject);
        User user =(User) userService.loadUserByUsername(principal.getName());

        if((space!=null)&&(user!=null) && (subject!=null)&&(space.getUser().getId()==user.getId())
                &&(spaceService.subjectExistSpace(space, idSubject))){
            SubSubject result = subSubjectService.get(idSubSubject);
            if(result!=null){
                return ResponseEntity.status(200).body(result);
            }else
                throw new SubSubjectNotFoundException();
        }else
            return ResponseEntity.status(403).body(null);
    }

    @Override
    public ResponseEntity<Iterable<SubSubject>> getAllSubSubjectBySubject(@PathVariable Long idSpace, Principal principal,@PathVariable Long idSubject) throws SpaceNotFoundException, SubjectNotFoundException {
        Space space= spaceService.get(idSpace);
        Subject subject = subjectService.get(idSubject);
        User user =(User) userService.loadUserByUsername(principal.getName());

        if((space!=null) && (user.getId()==space.getUser().getId())
                &&(spaceService.subjectExistSpace(space, idSubject))){
            return ResponseEntity.status(200).body(subSubjectService.getAllSubSubjectBySubject(subject));
        }else
            return ResponseEntity.status(403).body(null);
    }

    @Override
    public ResponseEntity<SubSubject> update(@PathVariable Long idSubject, @PathVariable Long idSpace,@RequestBody SubSubjectDto dto, Principal principal, @PathVariable Long idSubSubject) throws URISyntaxException, SubjectNotFoundException, SpaceNotFoundException, SubSubjectNotFoundException, SubSubjectExistException {
        Space space= spaceService.get(idSpace);
        Subject subject = subjectService.get(idSubject);
        User user =(User) userService.loadUserByUsername(principal.getName());
        MedicalSpecialties medicalSpecialties = medicalService.get(dto.getMedicalSpecialties());
        SubSubject subSubject = subSubjectService.get(idSubSubject);
        boolean subSubjectExist = false;

        if((space!=null) && (space.getUser().getId() == user.getId())
                &&(spaceService.subjectExistSpace(space, idSubject))&&(medicalSpecialties!=null)){

            List<SubSubject> subSubjects = subSubjectService.getAllSubSubjectBySubject(subject);
            System.out.println(subSubjects==null);
            if(subSubjects!=null) {
                for (SubSubject s : subSubjects) {
                    if (s.getTitle().equals(dto.getTitle())) {
                        subSubjectExist = true;
                    }
                }
            }
            if(!subSubjectExist){
                subSubject.setTitle(dto.getTitle());
                subSubject.setSubject(subject);
                subSubject.setMedicalSpecialties(medicalSpecialties);
                SubSubject result = subSubjectService.save(subSubject);
                return ResponseEntity.status(201).body(result);
            }else {
               throw  new SubSubjectExistException();
            }

        }else
            return ResponseEntity.status(403).body(null);
    }

    @Override
    public ResponseEntity<?> delete(Long idSpace, Long idSubject, Principal principal, Long idSubSubject) throws URISyntaxException, SubjectNotFoundException, SubSubjectNotFoundException {
        return null;
    }
}
