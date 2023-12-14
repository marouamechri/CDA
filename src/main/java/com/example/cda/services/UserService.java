package com.example.cda.services;

import com.example.cda.dtos.ResponseUser;
import com.example.cda.exceptions.AccountExistsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService  extends UserDetailsService {
    Authentication authenticate(String email, String password) throws Exception;

    UserDetails save(String username, String password, String name) throws AccountExistsException;

    UserDetails get(int id);
    //String forgotPassword(String mail);
    UserDetails findByUserName(String username);
    List<ResponseUser> getAllAdmin();


}
