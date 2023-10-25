package com.example.cda.modeles;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.util.Date;

@Entity
public class Consultation extends Event {

    @ManyToOne
    private DoctorUser doctor;
    @ManyToOne
    private MedicalSpecialties medicalSpecialties;

    public DoctorUser getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorUser doctor) {
        this.doctor = doctor;
    }

    public MedicalSpecialties getMedicalSpecialties() {
        return medicalSpecialties;
    }

    public void setMedicalSpecialties(MedicalSpecialties medicalSpecialties) {
        this.medicalSpecialties = medicalSpecialties;
    }

    public Consultation(){
        super();
    }
    public Consultation(Event event, DoctorUser doctor, MedicalSpecialties medicalSpecialties) {
        super(event.getDate(), event.isValidate(), event.getSubSubject(),event.getNatureAction());
        this.doctor = doctor;
        this.medicalSpecialties = medicalSpecialties;
    }
}
