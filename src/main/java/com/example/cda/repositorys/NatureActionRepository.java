package com.example.cda.repositorys;

import com.example.cda.modeles.NatureAction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NatureActionRepository extends CrudRepository<NatureAction, Integer> {
    NatureAction findByTitle(String title);
}
