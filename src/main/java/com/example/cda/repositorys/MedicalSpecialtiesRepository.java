package com.example.cda.repositorys;

import com.example.cda.modeles.MedicalSpecialties;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface MedicalSpecialtiesRepository extends CrudRepository<MedicalSpecialties, Integer> {
    MedicalSpecialties findBySpeciality(String speciality);
}
