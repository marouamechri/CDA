package com.example.cda.services;

import com.example.cda.dtos.ResponseUser;
import com.example.cda.modeles.Role;
import com.example.cda.modeles.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface RoleService {
    Role create(String name);

    void remove(int id);

    void attach(User user);

    void detach(User user);

    Iterable<Role> list();

    Role get(int id);
   void delete(Role role);
}
