package com.example.cda.repositorys;

import com.example.cda.modeles.DoctorUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorUserRepository extends CrudRepository<DoctorUser, Long> {

}
