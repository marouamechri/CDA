package com.example.cda.dtos;

import jakarta.validation.constraints.NotBlank;

public class NatureActionDto {
    private int id;
    @NotBlank
    private String title;

    public NatureActionDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
