package com.example.cda.dtos;


import com.example.cda.modeles.DoctorUser;
import com.example.cda.modeles.Hospital;
import com.example.cda.modeles.MedicalSpecialties;
import jakarta.persistence.ManyToOne;

import java.util.Date;

public class EventDto {

    private String date;
    private boolean validate;

    private int natureAction;
    private Long doctor;
    private int medicalSpecialties;

    private String dateFin;

    public EventDto() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public int getNatureAction() {
        return natureAction;
    }

    public void setNatureAction(int natureAction) {
        this.natureAction = natureAction;
    }

    public Long getDoctor() {
        return doctor;
    }

    public void setDoctor(Long doctor) {
        this.doctor = doctor;
    }

    public int getMedicalSpecialties() {
        return medicalSpecialties;
    }

    public void setMedicalSpecialties(int medicalSpecialties) {
        this.medicalSpecialties = medicalSpecialties;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }
}
