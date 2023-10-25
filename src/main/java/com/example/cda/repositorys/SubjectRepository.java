package com.example.cda.repositorys;

import com.example.cda.modeles.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ThreadPoolExecutor;
@Repository
public interface SubjectRepository extends CrudRepository<Subject, Long> {
}
