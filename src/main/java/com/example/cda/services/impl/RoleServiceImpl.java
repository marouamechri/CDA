package com.example.cda.services.impl;

import com.example.cda.modeles.Role;
import com.example.cda.modeles.User;
import com.example.cda.repositorys.RoleRepository;
import com.example.cda.repositorys.UserRepository;
import com.example.cda.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Role create(String name) {
        Role role = roleRepository.findByName(name);
        if(role!=null){
            return null;
        }else {
            Role result = new Role(0, name);
            roleRepository.save(result);
            return result;
        }

    }

    @Override
    public void remove(int id) {
        roleRepository.deleteById(id);
    }

    @Override
    public void attach(User user) {
        //System.out.println("service attach");
        Collection<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("USER"));
        roles.add(roleRepository.findByName("ADMIN"));

        user.setRoles(roles);

        userRepository.save(user);
    }

    @Override
    public void detach(User user) {
        Collection<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("USER"));

        user.setRoles(roles);
        userRepository.save(user);

    }

    @Override
    public Iterable<Role> list() {
        return roleRepository.findAll();
    }

    @Override
    public Role get(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }




}
