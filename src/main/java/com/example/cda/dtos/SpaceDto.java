package com.example.cda.dtos;

import jakarta.validation.constraints.NotBlank;

public class SpaceDto {
    private Long id;
    @NotBlank
    private String name;

    public SpaceDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
