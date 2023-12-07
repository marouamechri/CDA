package com.example.cda.controllers.user;

import com.example.cda.dtos.SpaceDto;
import com.example.cda.dtos.SubjectDto;
import com.example.cda.exceptions.SpaceExistException;
import com.example.cda.exceptions.SpaceNotFoundException;
import com.example.cda.exceptions.SubjectExistException;
import com.example.cda.exceptions.SubjectNotFoundException;
import com.example.cda.modeles.Space;
import com.example.cda.modeles.Subject;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.security.Principal;
@RequestMapping(path = "/user/spaces/{idSpace}")
@PreAuthorize("hasAuthority(\"USER\")")
public interface SubjectController {
    @PostMapping("/subject")
    ResponseEntity<Subject> save(@Valid @RequestBody SubjectDto dto, Principal principal, @PathVariable Long idSpace) throws SubjectExistException, URISyntaxException, SpaceNotFoundException;
    @GetMapping("/subject/{idSubject}")
    ResponseEntity<Subject> get(@PathVariable Long idSpace, Principal principal, @PathVariable Long idSubject) throws URISyntaxException, SubjectNotFoundException, SpaceNotFoundException;
    @GetMapping("/subject")
    ResponseEntity<Iterable<Subject>> getAllSubjectBySpace(@PathVariable Long idSpace, Principal principal) throws SpaceNotFoundException;
    @PutMapping("/subject/{idSubject}")
    public ResponseEntity<Subject> update(@PathVariable Long idSubject,@PathVariable Long idSpace,@Valid @RequestBody SubjectDto dto, Principal principal ) throws URISyntaxException, SubjectNotFoundException, SpaceNotFoundException;
    @DeleteMapping("/subject/{idSubject}")
    public ResponseEntity<?> delete(@PathVariable Long idSubject,@PathVariable Long idSpace, Principal principal) throws URISyntaxException, SubjectNotFoundException, SpaceNotFoundException;

}


