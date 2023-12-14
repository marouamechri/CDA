package com.example.cda.controllers;

import com.example.cda.dtos.AuthRequestDto;
import com.example.cda.dtos.AuthResponseDto;
import com.example.cda.exceptions.AccountExistsException;
import com.example.cda.exceptions.AccountNotExistsException;
import com.example.cda.services.JwtUserService;
import com.example.cda.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@CrossOrigin
@RestController
public class SecurityControllerImpl  implements SecurityController {
    @Autowired
    private JwtUserService jwtUserService;
    @Autowired
    RoleService roleService;
    @Override
    public ResponseEntity<AuthResponseDto> register(@RequestBody AuthRequestDto dto) throws AccountExistsException {
        UserDetails userDetails = jwtUserService.findByUserName(dto.getUsername());
        if (userDetails != null) {
            throw new AccountExistsException();
        }
        UserDetails user = jwtUserService.save(dto.getUsername(), dto.getPassword(), dto.getName());

        String jwtAccess = jwtUserService.generateJwtForUser(user);

        AuthResponseDto response = new AuthResponseDto();
        response.setAccessToken(jwtAccess);
        response.setUser(user);
        return ResponseEntity.ok(response);
    }
    @Override
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto dto) throws Exception {


        Authentication authentication = jwtUserService.authenticate(dto.getUsername(), dto.getPassword());
        //System.out.println("authentication:  "+authentication.getPrincipal());

        UserDetails user = (UserDetails) authentication.getPrincipal();
        //System.out.println("username"+user.getUsername());
        String jwtAccess = jwtUserService.generateJwtForUser(user);

        AuthResponseDto response = new AuthResponseDto();
        response.setAccessToken(jwtAccess);
        response.setUser(user);

        return ResponseEntity.ok(response);
    }



  /*  @Override
    public ResponseEntity<String> forgotPassword(String email) throws AccountNotExistsException {
        String response = jwtUserService.forgotPassword(email);

        return ResponseEntity.ok(response) ;

    }*/
}
