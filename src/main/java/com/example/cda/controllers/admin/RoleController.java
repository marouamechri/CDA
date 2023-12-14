package com.example.cda.controllers.admin;

import com.example.cda.dtos.ResponseUser;
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
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public interface RoleController {
    @PreAuthorize("hasAuthority(\"ADMIN\")")
    @GetMapping("/roles")
    ResponseEntity<Iterable<Role>> list() ;
    @PreAuthorize("hasAuthority('USER') and hasAuthority('ADMIN')")
    @PostMapping("/roles")
    ResponseEntity<Role> create(@Valid @RequestBody RoleDto dto) throws URISyntaxException ;
    @PostMapping("/attach")
    ResponseEntity<UserDetails> attach(@RequestBody String email) ;
    @PostMapping("/detach")
    ResponseEntity<UserDetails> detach(@RequestBody String email ) ;
    @DeleteMapping("/roles/{id}")
    ResponseEntity<?> delete(@PathVariable int id) ;
    @GetMapping ("")
    ResponseEntity<List<ResponseUser>> getListAdmin() ;

    }
