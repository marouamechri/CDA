package com.example.cda.repositorys;

import com.example.cda.modeles.SubSubject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubSubjectRepository extends CrudRepository<SubSubject,Long> {
}
