package com.example.cda.repositorys;

import com.example.cda.modeles.Analysis;
import com.example.cda.modeles.SubSubject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalysisRepository extends CrudRepository<Analysis, Long> {
    List<Analysis> findBySubSubject(SubSubject subSubject) ;
}
