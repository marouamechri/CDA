package com.example.cda.repositorys;

import com.example.cda.modeles.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ThreadPoolExecutor;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    public Role findByName(String name);

}
