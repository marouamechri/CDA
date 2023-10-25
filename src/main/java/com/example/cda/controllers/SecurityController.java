package com.example.cda.controllers;

import com.example.cda.dtos.AuthRequestDto;
import com.example.cda.dtos.AuthResponseDto;
import com.example.cda.exceptions.AccountExistsException;
import com.example.cda.exceptions.AccountNotExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface SecurityController {
    @PostMapping("/signup")
    ResponseEntity<AuthResponseDto> register(@RequestBody AuthRequestDto dto)throws AccountExistsException ;
    @PostMapping("/login")
    ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto dto) throws Exception;

   /* @PostMapping("/forgotPassword")
    ResponseEntity<String > forgotPassword(@RequestBody String email)throws AccountNotExistsException;*/



}
