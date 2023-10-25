package com.example.cda.services;

import com.example.cda.modeles.DoctorUser;
import com.example.cda.modeles.MedicalSpecialties;

import java.util.List;

public interface MedicalSpecialtiesService {
    MedicalSpecialties save(String speciality);
    MedicalSpecialties get(int id);
    Iterable<MedicalSpecialties> getAll();
    void delete(MedicalSpecialties specialitySante);
    MedicalSpecialties update(MedicalSpecialties specialitySante, String speciality);
    List<MedicalSpecialties> getSpecialitiesByDoctor(DoctorUser doctor);
}
