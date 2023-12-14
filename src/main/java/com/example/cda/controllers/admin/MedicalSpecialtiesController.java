package com.example.cda.controllers.admin;

import com.example.cda.dtos.MedicalSpecialtiesDto;
import com.example.cda.exceptions.SpecialityExistException;
import com.example.cda.exceptions.SpecialityNotFoundException;
import com.example.cda.modeles.MedicalSpecialties;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
@CrossOrigin(origins = "*")

public interface MedicalSpecialtiesController {
    @PreAuthorize("hasAuthority(\"ADMIN\")")
    @PostMapping("/admin/medicalSpecialties")
    ResponseEntity<MedicalSpecialties> createSpeciality(@Valid @RequestBody MedicalSpecialtiesDto dto) throws URISyntaxException, SpecialityExistException;
    @PreAuthorize("hasAuthority(\"USER\")")
    @GetMapping("/user/medicalSpecialties/{id}")
    ResponseEntity<MedicalSpecialties> getSpeciality(@PathVariable int id) throws SpecialityNotFoundException;
    @PreAuthorize("hasAuthority(\"USER\")")
    @GetMapping("/user/medicalSpecialties")
    ResponseEntity<Iterable<MedicalSpecialties>> getAllSpeciality();
    @PreAuthorize("hasAuthority(\"ADMIN\")")
    @DeleteMapping("/admin/medicalSpecialties/{id}")
    public ResponseEntity<?> deleteSpeciality(@PathVariable int id) throws URISyntaxException, SpecialityNotFoundException;
    @PreAuthorize("hasAuthority(\"ADMIN\")")
    @PutMapping("/admin/medicalSpecialties/{id}")
    public ResponseEntity<MedicalSpecialties> updateSpeciality(@PathVariable int id ,@Valid @RequestBody MedicalSpecialtiesDto dto) throws URISyntaxException, SpecialityNotFoundException;





    }
