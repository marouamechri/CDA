package com.example.cda.dtos;

import jakarta.validation.constraints.NotBlank;

public class RoleDto {
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
