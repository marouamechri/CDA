package com.example.cda.services;

import com.example.cda.modeles.Analysis;
import com.example.cda.modeles.Treatment;

public interface TreatmentService {
    Treatment save(Treatment treatment);
    Treatment get (Long idTreatment );
    void delete (Treatment treatment);
}
