package com.example.cda.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtUserService extends UserService {
    String generateJwtForUser(UserDetails user);
    UserDetails getUserFromJwt(String jwt);
    boolean validateToken(String token, UserDetails userDetails);
    Claims getAllClaimsFromToken(String token);
    String getUserNameFromJwt(String JwtToken);
    boolean isExpiredJwtToken(String token);
    UserDetails loadUserByUsername(String username);
}
