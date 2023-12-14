package com.example.cda.controllers.admin.impl;

import com.example.cda.controllers.admin.RoleController;
import com.example.cda.dtos.ResponseUser;
import com.example.cda.dtos.RoleDto;
import com.example.cda.exceptions.RoleExistException;
import com.example.cda.exceptions.UserNotFoundException;
import com.example.cda.modeles.Role;
import com.example.cda.modeles.User;
import com.example.cda.services.RoleService;
import com.example.cda.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RoleNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

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
        if(role==null){
             throw new RoleExistException();
        }else{
            URI uri = new URI("/roles/" + role.getId());
            return ResponseEntity.created(uri).body(role);
        }

    }

    @Override
    public ResponseEntity<UserDetails> attach(String email){
        System.out.println("attache"+email);
        User user = (User)  userService.findByUserName(email);
        if (user == null) {
            return ResponseEntity.ok(null);
        }
        roleService.attach(user);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<UserDetails> detach(String email){
        User user = (User) userService.findByUserName(email);
        if (user == null) {
            return ResponseEntity.ok(null);
        }

        roleService.detach(user);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<?> delete(int id) {
        Role role = roleService.get(id);
        if(role == null){
            return ResponseEntity.notFound().build();
        }
        roleService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ResponseUser>> getListAdmin() {
        return ResponseEntity.ok(userService.getAllAdmin()) ;
    }
}
