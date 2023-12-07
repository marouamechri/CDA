package com.example.cda.services.impl;

import com.example.cda.modeles.DoctorUser;
import com.example.cda.modeles.MedicalSpecialties;
import com.example.cda.repositorys.MedicalSpecialtiesRepository;
import com.example.cda.services.MedicalSpecialtiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalSpecialtiesServicesImpl implements MedicalSpecialtiesService {
    @Autowired
    MedicalSpecialtiesRepository medicalRepository;

    @Override
    public MedicalSpecialties save(MedicalSpecialties medicalSpecialties) {
        MedicalSpecialties medicalSp= medicalRepository.findBySpeciality(medicalSpecialties.getSpeciality());
        if(medicalSp == null){
            medicalSp =   new MedicalSpecialties();
            medicalSp.setSpeciality(medicalSpecialties.getSpeciality());
            medicalSp.setIcone(medicalSpecialties.getIcone());
            return medicalRepository.save(medicalSp);
        }
        return null;
    }

    @Override
    public MedicalSpecialties get(int id) {
        return medicalRepository.findById(id).orElse(null);

    }

    @Override
    public Iterable<MedicalSpecialties> getAll() {
        return medicalRepository.findAll();
    }

    @Override
    public void delete(MedicalSpecialties specialitySante) {
        medicalRepository.delete(specialitySante);

    }

    @Override
    public MedicalSpecialties update(MedicalSpecialties specialitySante, String speciality) {
        MedicalSpecialties result = medicalRepository.findBySpeciality(specialitySante.getSpeciality());
        result.setSpeciality(speciality);
        return medicalRepository.save(result);
    }

    @Override
    public List<MedicalSpecialties> getSpecialitiesByDoctor(DoctorUser doctor) {
        Iterable<MedicalSpecialties> medicalSpecialties = medicalRepository.findAll();
        List<MedicalSpecialties> result =  new ArrayList<>();
        for (MedicalSpecialties m:medicalSpecialties) {
            for (DoctorUser d :m.getDoctors()) {

                if(d.getId() == doctor.getId()){
                    result.add(m);
                }
            }
        }
        return result;
    }
}
