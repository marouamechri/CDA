package com.example.cda.repositorys;

import com.example.cda.modeles.Consultation;
import com.example.cda.modeles.DoctorUser;
import com.example.cda.modeles.MedicalSpecialties;
import com.example.cda.modeles.SubSubject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultationRepository extends CrudRepository<Consultation, Long> {
    List<Consultation>findByMedicalSpecialties(MedicalSpecialties medicalSpecialties);
    List<Consultation>findByDoctor(DoctorUser doctor);
    List<Consultation> findBySubSubject(SubSubject subSubject);
}
