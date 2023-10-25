package com.example.cda.controllers.admin.impl;

import com.example.cda.controllers.admin.RoleController;
import com.example.cda.dtos.RoleDto;
import com.example.cda.exceptions.UserNotFoundException;
import com.example.cda.modeles.Role;
import com.example.cda.services.RoleService;
import com.example.cda.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class RoleControllerImpl implements RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<Iterable<Role>> list() {
        return ResponseEntity.ok(roleService.list()); // Sending a 200 HTTP status code

    }

    @Override
    public ResponseEntity<Role> create(RoleDto dto) throws URISyntaxException {
        Role role = roleService.create(dto.getName());
        URI uri = new URI("/roles/" + role.getId());
        return ResponseEntity.created(uri).body(role);

    }

    @Override
    public ResponseEntity<UserDetails> attach(int roleId, int userId) throws UserNotFoundException {
        UserDetails user = userService.get(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        roleService.attach(user, roleId);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<UserDetails> detach(int roleId, int userId) throws UserNotFoundException {
        UserDetails user = userService.get(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        roleService.detach(user, roleId);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<?> delete(int id) {
        roleService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
