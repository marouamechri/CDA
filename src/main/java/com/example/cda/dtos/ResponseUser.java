package com.example.cda.dtos;

import com.example.cda.modeles.Role;

import java.util.List;

public class ResponseUser {
   private String name;
   private  String email;

    public ResponseUser(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
