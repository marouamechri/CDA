package com.example.cda.services;

import com.example.cda.modeles.Consultation;
import com.example.cda.modeles.DoctorUser;
import com.example.cda.modeles.MedicalSpecialties;
import com.example.cda.modeles.SubSubject;

import java.util.List;

public interface ConsultationService {
    Consultation save(Consultation consultation);
    Consultation get (Long idConsultation );
    List<Consultation> getAllConsultationBySubSubject(SubSubject subSubject);
    void delete (Consultation consultation);
    List<Consultation>getByMedicalSpeciality(List<Consultation> consultationList, MedicalSpecialties medicalSpecialties);
    List<Consultation>getByDoctor(List<Consultation> consultationList, DoctorUser doctorUser);
}
