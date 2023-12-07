package com.example.cda.dtos;

import jakarta.validation.constraints.NotBlank;

public class MedicalSpecialtiesDto {
    private int  id;
    @NotBlank
    private String  speciality;
    private String icone;

    public MedicalSpecialtiesDto(int id, String speciality) {
        this.id = id;
        this.speciality = speciality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }
}
