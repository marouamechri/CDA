package com.example.cda.services.impl;

import com.example.cda.modeles.Treatment;
import com.example.cda.repositorys.TreatmentRepository;
import com.example.cda.services.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TreatmentServiceIpm implements TreatmentService {
    @Autowired
    TreatmentRepository treatmentRepository;
    @Override
    public Treatment save(Treatment treatment) {
        return treatmentRepository.save(treatment);
    }

    @Override
    public Treatment get(Long idTreatment) {
        return treatmentRepository.findById(idTreatment).orElse(null);
    }

    @Override
    public void delete(Treatment treatment) {
        treatmentRepository.delete(treatment);
    }
}
