package com.example.cda.dtos;

import org.springframework.security.core.userdetails.UserDetails;

public class AuthResponseDto {
    private String accessToken;
    private UserDetails user;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserDetails getUser() {
        return user;
    }

    public void setUser(UserDetails user) {
        this.user = user;
    }

}
