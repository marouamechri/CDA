package com.example.cda.modeles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class MedicalSpecialties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String speciality;
    @JsonIgnore
    @ManyToMany(mappedBy = "medicalSpecialties",fetch = FetchType.LAZY)
    private List<DoctorUser> doctors;

    @JsonIgnore
    @OneToMany(mappedBy = "medicalSpecialties",fetch = FetchType.LAZY)
    private List<Consultation> consultations;
    @JsonIgnore
    @OneToMany(mappedBy = "medicalSpecialties",fetch = FetchType.LAZY)
    private List<SubSubject> subSubjects;

    public MedicalSpecialties(String speciality) {
        this.speciality = speciality;
    }

    public MedicalSpecialties(){}

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

    public List<DoctorUser> getDoctors() {
        return doctors;
    }

    public List<SubSubject> getSubSubjects() {
        return subSubjects;
    }
}
