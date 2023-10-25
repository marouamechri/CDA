package com.example.cda.modeles;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    @ManyToOne
    private Event event;
    @OneToOne
    private Event eventValidate;
    @OneToOne
    private Event nextEvent;
    private String typeTask;

    private int state;
    @ManyToOne
    private NatureAction natureAction;
    @ManyToMany
    List<Document> prescription;

    public Task(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Event getEventValidate() {
        return eventValidate;
    }

    public void setEventValidate(Event eventValidate) {
        this.eventValidate = eventValidate;
    }

    public Event getNextEvent() {
        return nextEvent;
    }

    public void setNextEvent(Event nextEvent) {
        this.nextEvent = nextEvent;
    }

    public String getTypeTask() {
        return typeTask;
    }

    public void setTypeTask(String typeTask) {
        this.typeTask = typeTask;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public NatureAction getNatureAction() {
        return natureAction;
    }

    public void setNatureAction(NatureAction natureAction) {
        this.natureAction = natureAction;
    }

    public List<Document> getPrescription() {
        return prescription;
    }

    public void setPrescription(List<Document> prescription) {
        this.prescription = prescription;
    }
}
