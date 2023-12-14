package com.example.cda.controllers.user.impl;

import com.example.cda.controllers.user.SubjectController;
import com.example.cda.dtos.SubjectDto;
import com.example.cda.exceptions.SpaceNotFoundException;
import com.example.cda.exceptions.SubjectExistException;
import com.example.cda.exceptions.SubjectNotFoundException;
import com.example.cda.modeles.Space;
import com.example.cda.modeles.Subject;
import com.example.cda.modeles.User;
import com.example.cda.services.SpaceService;
import com.example.cda.services.SubjectService;
import com.example.cda.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
public class SubjectControllerImpl implements SubjectController {
    @Autowired
    SubjectService subjectService;
    @Autowired
    SpaceService spaceService;
    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<Subject> save(SubjectDto dto, Principal principal, @PathVariable Long idSpace) throws SubjectExistException, URISyntaxException, SpaceNotFoundException {

        //contoler si le user sapace est le même principale
        Space space= spaceService.get(idSpace);
        User user =(User) userService.loadUserByUsername(principal.getName());
        boolean subjectExist = false;

        if((space!=null) && (user.getId() == space.getUser().getId())){
            Iterable<Subject> subjects = subjectService.getAllSubjectBySpace(space);
            if(subjects!=null) {
                for (Subject s : subjects) {
                    if (s.getTitle().equals(dto.getTitle())) {
                        subjectExist = true;
                    }
                }
            }
            if(!subjectExist){

                Subject subject = new Subject();
                subject.setTitle(dto.getTitle());
                subject.setSpace(space);
                Subject result = subjectService.save(subject);

                return ResponseEntity.status(201).body(result);
            }else {
                return ResponseEntity.status(201).body(null);
            }

        }else
            throw new SpaceNotFoundException();


    }

    @Override
    public ResponseEntity<Subject> get(Long idSpace, Principal principal,@PathVariable Long idSubject) throws URISyntaxException, SubjectNotFoundException, SpaceNotFoundException {
        Space space =  spaceService.get(idSpace);
        if(space==null){
            throw new SpaceNotFoundException();
        }
        User user =(User) userService.loadUserByUsername(principal.getName());
        if((user.getId() == space.getUser().getId())&&(spaceService.subjectExistSpace(space, idSubject)) ){

            Subject result = subjectService.get(idSubject);
            return ResponseEntity.status(200).body(result);

        }
        return ResponseEntity.status(403).body(null);
    }

    @Override
    public ResponseEntity<Iterable<Subject>> getAllSubjectBySpace(@PathVariable Long idSpace, Principal principal) throws SpaceNotFoundException {
        Space space =  spaceService.get(idSpace);
        if(space==null){
            throw new SpaceNotFoundException();
        }
        User user =(User) userService.loadUserByUsername(principal.getName());
        if(user.getId()==space.getUser().getId() ){

           Iterable<Subject>  result = subjectService.getAllSubjectBySpace(space);
            return ResponseEntity.status(200).body(result);

        }
        return ResponseEntity.status(403).body(null);
    }

    @Override
    public ResponseEntity<Subject> update(@PathVariable Long idSubject, @PathVariable Long idSpace, SubjectDto dto, Principal principal) throws URISyntaxException, SubjectNotFoundException, SpaceNotFoundException {

        Space space =  spaceService.get(idSpace);
        if(space==null){
            throw new SpaceNotFoundException();
        }
        User user =(User) userService.loadUserByUsername(principal.getName());
        if((user.getId()==space.getUser().getId()) && (spaceService.subjectExistSpace(space, idSubject))){
            Subject subject = subjectService.get(idSubject);
            if(subject!=null){
                Subject result =  subjectService.update(subject, dto.getTitle());
                return ResponseEntity.status(200).body(result);
            }else
                throw new SpaceNotFoundException();

        }
        return ResponseEntity.status(403).body(null);

    }

    @Override
    public ResponseEntity<?> delete(@PathVariable Long idSubject,@PathVariable Long idSpace, Principal principal) throws URISyntaxException, SubjectNotFoundException, SpaceNotFoundException {
        Space space = spaceService.get(idSpace);
        if (space == null) {
            throw new SpaceNotFoundException();
        }
        User user = (User) userService.loadUserByUsername(principal.getName());
        if ((user.getId() == space.getUser().getId()) && (spaceService.subjectExistSpace(space, idSubject))) {
            Subject subject = subjectService.get(idSubject);
            if (subject == null) {
                return ResponseEntity.status(500).body(null);
                } else
                    subjectService.delete(subject);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(403).body(null);
        }
    }


