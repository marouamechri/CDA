package com.example.cda.dtos;

import com.example.cda.modeles.Space;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

public class SubjectDto {
    @NotBlank
    private String title;
    protected Space space;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }
}
