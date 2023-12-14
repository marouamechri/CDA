package com.example.cda.controllers.admin;

import com.example.cda.dtos.NatureActionDto;
import com.example.cda.exceptions.NatureExistException;
import com.example.cda.exceptions.NatureNotFoundException;
import com.example.cda.modeles.NatureAction;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;


@CrossOrigin(origins = "*")
public interface NatureActionController {
    @PreAuthorize("hasAuthority('USER') and hasAuthority('ADMIN')")
    @PostMapping("/admin/natureAction")
    ResponseEntity<NatureAction> createNatureAction(@Valid @RequestBody NatureActionDto dto) throws NatureExistException, URISyntaxException;
    @PreAuthorize("hasAuthority(\"USER\")")
    @GetMapping("/user/natureAction/{id}")
    ResponseEntity<NatureAction> getNatureAction(@PathVariable int id) throws NatureNotFoundException;

    @PreAuthorize("hasAuthority(\"USER\")")
    @GetMapping("/user/natureAction")
    ResponseEntity<List<NatureAction>> getAllNatureEvent();
    @PreAuthorize("hasAuthority(\"USER\")")
    @GetMapping("/user/natureTask")
    ResponseEntity<List<NatureAction>> getAllNatureTask();

    @PreAuthorize("hasAuthority(\"ADMIN\")")
    @DeleteMapping("/admin/natureAction/{id}")
    ResponseEntity<?> deleteNatureAction(@PathVariable int id)throws NatureNotFoundException;
    @PreAuthorize("hasAuthority('USER') and hasAuthority('ADMIN')")
    @PutMapping("/admin/natureAction/{id}")
    ResponseEntity<NatureAction> updateNatureAction( @Valid @RequestBody NatureActionDto dto, @PathVariable int id) throws NatureNotFoundException;


}
