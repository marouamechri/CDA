package com.example.cda.repositorys;

import com.example.cda.modeles.Hospitalization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalizationRepository extends CrudRepository<Hospitalization, Long> {
}
