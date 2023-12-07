package com.example.cda.modeles;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class DoctorUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name ;
    private String address;
    private  String phone;
    @ManyToMany
    private List<MedicalSpecialties> medicalSpecialties= new ArrayList<>();
    @ManyToMany
    private List<Space> spaces= new ArrayList<>();



    public DoctorUser(){}

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Space> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<Space> spaces) {
        this.spaces = spaces;
    }

    public List<MedicalSpecialties> getMedicalSpecialties() {
        return medicalSpecialties;
    }

    public void setMedicalSpecialties(List<MedicalSpecialties> medicalSpecialties) {
        this.medicalSpecialties = medicalSpecialties;
    }
}
