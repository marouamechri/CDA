package com.example.cda.controllers.admin;

import com.example.cda.dtos.RoleDto;
import com.example.cda.exceptions.UserNotFoundException;
import com.example.cda.modeles.Role;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.net.URISyntaxException;

@RequestMapping("/dashboard/admin")

public interface RoleController {
    @PreAuthorize("hasAuthority(\"ADMIN\")")
    @GetMapping("/roles")
    ResponseEntity<Iterable<Role>> list() ;
    @PreAuthorize("hasAuthority(\"ADMIN\")")
    @PostMapping("/roles")
    ResponseEntity<Role> create(@Valid @RequestBody RoleDto dto) throws URISyntaxException ;
    @PreAuthorize("hasAuthority(\"ADMIN\")")
    @PostMapping("/roles/{roleId}/users/{userId}/attach")
    ResponseEntity<UserDetails> attach(@PathVariable int roleId, @PathVariable int userId) throws UserNotFoundException, RoleNotFoundException;
    @PreAuthorize("hasAuthority(\"ADMIN\")")
    @PostMapping("/roles/{roleId}/users/{userId}/detach")
    ResponseEntity<UserDetails> detach(@PathVariable int roleId, @PathVariable int userId) throws UserNotFoundException, RoleNotFoundException;
    @PreAuthorize("hasAuthority(\"ADMIN\")")
    @DeleteMapping("/roles/{id}")
    ResponseEntity<?> delete(@PathVariable int id) ;



    }
