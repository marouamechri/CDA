package com.example.cda.modeles;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public  class Event {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected Long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    protected Date date;
    protected boolean validate;
    @ManyToOne
    protected SubSubject subSubject;
    @ManyToOne
    protected NatureAction natureAction;
    @ManyToMany
    List<Document> prescription;

    @OneToMany(mappedBy = "event",fetch = FetchType.LAZY)
    @JsonIgnore
    protected List<Task> tasks= new ArrayList<>();

    public Event(Date date, boolean validate, SubSubject subSubject, NatureAction natureAction) {
        this.date = date;
        this.validate = validate;
        this.subSubject = subSubject;
        this.natureAction = natureAction;
    }

    public Event() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public SubSubject getSubSubject() {
        return subSubject;
    }

    public void setSubSubject(SubSubject subSubject) {
        this.subSubject = subSubject;
    }

    public NatureAction getNatureAction() {
        return natureAction;
    }

    public void setNatureAction(NatureAction natureAction) {
        this.natureAction = natureAction;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Document> getPrescription() {
        return prescription;
    }

    public void setPrescription(List<Document> prescription) {
        this.prescription = prescription;
    }
}
