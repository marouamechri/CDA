package com.example.cda.dtos;

import com.example.cda.modeles.MedicalSpecialties;
import com.example.cda.modeles.Space;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.List;

public class DoctorUserDto {

    private String name ;
    private String address;
    private  String phone;
    private int medicalSpecialty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMedicalSpecialty() {
        return medicalSpecialty;
    }

    public void setMedicalSpecialty(int medicalSpecialty) {
        this.medicalSpecialty = medicalSpecialty;
    }

}
