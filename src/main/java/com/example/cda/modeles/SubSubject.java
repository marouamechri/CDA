package com.example.cda.modeles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class SubSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Subject subject;
    private String title;
    @ManyToOne
    private MedicalSpecialties medicalSpecialties;

    @OneToMany(mappedBy = "subSubject", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Event> events =new ArrayList<>();


    public SubSubject(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MedicalSpecialties getMedicalSpecialties() {
        return medicalSpecialties;
    }

    public void setMedicalSpecialties(MedicalSpecialties medicalSpecialties) {
        this.medicalSpecialties = medicalSpecialties;
    }

   public List<Event> getEvents() {
        return events;
    }
}
