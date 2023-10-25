package com.example.cda.modeles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String name;
    @ManyToOne
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "space",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Subject> subjects =new ArrayList<>();
    @JsonIgnore
    @ManyToMany(mappedBy = "spaces",fetch = FetchType.LAZY)
    private List<DoctorUser> doctorUsers= new ArrayList<>();


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

    public List<Subject> getSubjects() {
        return subjects;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<DoctorUser> getDoctorUsers() {
        return doctorUsers;
    }

    public  Space(){}

    public Space(String name, User user, List<Subject> subjects) {
        this.name = name;
        this.user = user;
        this.subjects = subjects;
    }
}
