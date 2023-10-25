package com.example.cda.modeles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected Long id;

    private String name;
    @JsonIgnore
    @ManyToMany(mappedBy ="prescription" )
    private List<Event> events;
    @JsonIgnore
    @ManyToMany(mappedBy = "prescription")
    private List<Task> tasks;
    @ManyToOne
    private Analysis resultAnalyse;

    public Document(){};
    public Document(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public List<Event> getEvents() {
        return events;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Analysis getResultAnalyse() {
        return resultAnalyse;
    }

    public void setResultAnalyse(Analysis resultAnalyse) {
        this.resultAnalyse = resultAnalyse;
    }
}
