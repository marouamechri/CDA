package com.example.cda.services.impl;

import com.example.cda.modeles.Consultation;
import com.example.cda.modeles.DoctorUser;
import com.example.cda.modeles.MedicalSpecialties;
import com.example.cda.modeles.SubSubject;
import com.example.cda.repositorys.ConsultationRepository;
import com.example.cda.services.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultationServiceImpl implements ConsultationService {
    @Autowired
    ConsultationRepository consultationRepository;

    @Override
    public Consultation save(Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    @Override
    public Consultation get(Long idConsultation) {
        return consultationRepository.findById(idConsultation).orElse(null);
    }

    @Override
    public List<Consultation> getAllConsultationBySubSubject(SubSubject subSubject) {
        return consultationRepository.findBySubSubject(subSubject);
    }

    @Override
    public void delete(Consultation consultation) {
        consultationRepository.delete(consultation);
    }

    @Override
    public List<Consultation> getByMedicalSpeciality(List<Consultation> consultationList, MedicalSpecialties medicalSpecialties) {
        List<Consultation> result = new ArrayList<>();
        for (Consultation c:consultationList) {
            if(c.getMedicalSpecialties().getId()== medicalSpecialties.getId())
                result.add(c);

        }

        return result;
    }

    @Override
    public List<Consultation> getByDoctor(List<Consultation> consultationList, DoctorUser doctorUser) {
        List<Consultation> result = new ArrayList<>();
        for (Consultation c:consultationList) {
            if(c.getDoctor().getId()== doctorUser.getId())
                result.add(c);

        }

        return result;
    }
}
