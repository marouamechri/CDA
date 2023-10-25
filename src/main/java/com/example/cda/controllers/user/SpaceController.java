package com.example.cda.controllers.user;

import com.example.cda.dtos.SpaceDto;
import com.example.cda.exceptions.SpaceExistException;
import com.example.cda.exceptions.SpaceNotFoundException;
import com.example.cda.exceptions.UserNotFoundException;
import com.example.cda.modeles.Space;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;

@RequestMapping(path = "/user")
public interface SpaceController {
    @PostMapping("/spaces")
    ResponseEntity<Space> save(@Valid @RequestBody SpaceDto dto, Principal principal) throws SpaceExistException, URISyntaxException, UserNotFoundException;
    @GetMapping("/spaces/{id}")
    ResponseEntity<Space> get(@PathVariable Long id, Principal principal) throws URISyntaxException, SpaceNotFoundException;
    @GetMapping("/spaces")
    ResponseEntity<List<Space>> getAllSpaceByUser(Principal principal)  ;

    @PutMapping("/spaces/{id}")
    public ResponseEntity<Space> update(@PathVariable Long id,@Valid @RequestBody SpaceDto dto, Principal principal ) throws URISyntaxException, SpaceNotFoundException;

    @DeleteMapping("/spaces/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) throws URISyntaxException, SpaceNotFoundException;

    }

