package com.example.cda.controllers.admin.impl;

import com.example.cda.controllers.admin.MedicalSpecialtiesController;
import com.example.cda.dtos.MedicalSpecialtiesDto;
import com.example.cda.exceptions.SpecialityExistException;
import com.example.cda.exceptions.SpecialityNotFoundException;
import com.example.cda.modeles.MedicalSpecialties;
import com.example.cda.services.MedicalSpecialtiesService;
import com.example.cda.services.impl.MedicalSpecialtiesServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
public class MedicalSpecialtiesControllerImp implements MedicalSpecialtiesController {
    @Autowired
    MedicalSpecialtiesService specialityService = new MedicalSpecialtiesServicesImpl();

    @Override
    public ResponseEntity<MedicalSpecialties> createSpeciality(MedicalSpecialtiesDto dto) throws URISyntaxException, SpecialityExistException {
        MedicalSpecialties specialityResult = specialityService.save(dto.getSpeciality());
        if(specialityResult==null){
            throw new SpecialityExistException();
        }
        return ResponseEntity.status(201).body(specialityResult);    }

    @Override
    public ResponseEntity<MedicalSpecialties> getSpeciality(int id) throws SpecialityNotFoundException {
        MedicalSpecialties specialityResult = specialityService.get(id);
        if(specialityResult==null){
            throw new SpecialityNotFoundException();
        }
        return ResponseEntity.status(200).body(specialityResult);
    }

    @Override
    public ResponseEntity<Iterable<MedicalSpecialties>> getAllSpeciality() {
        Iterable<MedicalSpecialties>  listSpeciality = specialityService.getAll();
        return ResponseEntity.ok(listSpeciality);

    }

    @Override
    public ResponseEntity<?> deleteSpeciality(int id) throws URISyntaxException, SpecialityNotFoundException {
       MedicalSpecialties specialitySante = specialityService.get(id);
        if(specialitySante==null){
            throw new SpecialityNotFoundException();
        }
        specialityService.delete(specialitySante);
        return ResponseEntity.noContent().build();

    }

    @Override
    public ResponseEntity<MedicalSpecialties> updateSpeciality(int id, MedicalSpecialtiesDto dto) throws URISyntaxException, SpecialityNotFoundException {
        MedicalSpecialties specialitySante = specialityService.get(id);
        if(specialitySante==null){
            throw new SpecialityNotFoundException();
        }
        MedicalSpecialties result =  specialityService.update(specialitySante, dto.getSpeciality());
        return ResponseEntity.ok(result);

    }
}
