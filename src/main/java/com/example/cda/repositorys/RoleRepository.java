package com.example.cda.repositorys;

import com.example.cda.modeles.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(String name);

}
