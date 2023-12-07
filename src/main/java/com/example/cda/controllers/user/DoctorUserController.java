package com.example.cda.controllers.user;

import com.example.cda.dtos.DoctorUserDto;
import com.example.cda.dtos.SubjectDto;
import com.example.cda.exceptions.*;
import com.example.cda.modeles.DoctorUser;
import com.example.cda.modeles.MedicalSpecialties;
import com.example.cda.modeles.Subject;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
@RequestMapping(path = "/user/spaces")
@PreAuthorize("hasAuthority(\"USER\")")
public interface DoctorUserController {

    @PostMapping("/{idSpace}/doctor")
    ResponseEntity<DoctorUser> save(@Valid @RequestBody DoctorUserDto dto, Principal principal, @PathVariable Long idSpace) throws DoctorUserExistException, URISyntaxException, SpaceNotFoundException;
    @GetMapping("/{idSpace}/doctor/{idDoctor}")
    ResponseEntity<DoctorUser> get(@PathVariable Long idSpace, Principal principal, @PathVariable Long idDoctor) throws URISyntaxException, DoctorUserNotFoundException, SpaceNotFoundException;
    @GetMapping("/{idSpace}/doctor")
    ResponseEntity<Iterable<DoctorUser>> getAllDoctorBySpace(@PathVariable Long idSpace, Principal principal) throws SpaceNotFoundException;
    @GetMapping("/doctor")
    ResponseEntity<Iterable<DoctorUser>> getAllDoctorByUser( Principal principal) throws UserNotFoundException;

    @PutMapping("/{idSpace}/doctor/{idDoctor}")
    ResponseEntity<DoctorUser> update(@PathVariable Long idDoctor, @PathVariable Long idSpace, @RequestBody DoctorUserDto dto, Principal principal) throws URISyntaxException, DoctorUserNotFoundException, SpaceNotFoundException, DoctorUserExistException;

    @DeleteMapping("/{idSpace}/doctor/{idDoctor}")
    ResponseEntity<?> delete(@PathVariable Long idDoctor, Principal principal) throws URISyntaxException, DoctorUserNotFoundException;
    @GetMapping("/{idSpace}/doctor/{idDoctor}/medicalSpecialties")
    ResponseEntity<List<MedicalSpecialties>> getMedicalSpecialtiesByDoctor(@PathVariable Long idSpace, Principal principal, @PathVariable Long idDoctor) throws URISyntaxException, DoctorUserNotFoundException, SpaceNotFoundException;


}
