package com.example.cda.controllers.user;

import com.example.cda.dtos.SubSubjectDto;
import com.example.cda.dtos.SubjectDto;
import com.example.cda.exceptions.*;
import com.example.cda.modeles.SubSubject;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.security.Principal;

@RequestMapping(path = "/user/spaces/{idSpace}/subject/{idSubject}")

public interface SubSubjectController {

    @PostMapping("/subSubject")
    ResponseEntity<SubSubject> save(@RequestBody SubSubjectDto dto, Principal principal, @PathVariable Long idSpace,@PathVariable Long idSubject ) throws SubSubjectExistException, URISyntaxException, SpaceNotFoundException, SubjectNotFoundException;
    @GetMapping("/subSubject/{idSubSubject}")
    ResponseEntity<SubSubject> get(@PathVariable Long idSpace, Principal principal, @PathVariable Long idSubject, @PathVariable Long idSubSubject) throws URISyntaxException, SubjectNotFoundException, SpaceNotFoundException, SubSubjectNotFoundException;
    @GetMapping("/subSubject")
    ResponseEntity<Iterable<SubSubject>> getAllSubSubjectBySubject(@PathVariable Long idSpace, Principal principal, @PathVariable Long idSubject) throws SpaceNotFoundException, SubjectNotFoundException;

    @PutMapping("/subSubject/{idSubSubject}")
    public ResponseEntity<SubSubject> update(@PathVariable Long idSubject,@PathVariable Long idSpace,@Valid @RequestBody SubSubjectDto dto, Principal principal, @PathVariable Long idSubSubject ) throws URISyntaxException, SubjectNotFoundException, SpaceNotFoundException, SubSubjectNotFoundException, SubSubjectExistException;

    @DeleteMapping("/subSubject/{idSubSubject}")
    public ResponseEntity<?> delete(@PathVariable Long idSpace,@PathVariable Long idSubject, Principal principal, @PathVariable Long idSubSubject) throws URISyntaxException, SubjectNotFoundException, SubSubjectNotFoundException;

}
