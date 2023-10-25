package com.example.cda.modeles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Entity
@Transactional
public class NatureAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    protected String title;

    @JsonIgnore
    @OneToMany(mappedBy = "natureAction",fetch = FetchType.LAZY)
    private List<Event> events = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "natureAction",fetch = FetchType.LAZY)
    private List<Task> tasks =new ArrayList<>();

    public NatureAction(){
    }

    public NatureAction(int id, String title) {
        this.id=id;
        this.title = title;
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
