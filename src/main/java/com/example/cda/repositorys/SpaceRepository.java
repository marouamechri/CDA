package com.example.cda.repositorys;

import com.example.cda.modeles.Space;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends CrudRepository<Space, Long> {
     Space findByName(String name);

}
