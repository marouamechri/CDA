package com.example.cda.dtos;

import com.example.cda.modeles.Event;

public class TaskDto {

    private String description;
    private Long idEventValidate;
    private Long idNextEvent;
    private int state;
    private int idNatureAction;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIdEventValidate() {
        return idEventValidate;
    }

    public void setIdEventValidate(Long idEventValidate) {
        this.idEventValidate = idEventValidate;
    }

    public Long getIdNextEvent() {
        return idNextEvent;
    }

    public void setIdNextEvent(Long idNextEvent) {
        this.idNextEvent = idNextEvent;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIdNatureAction() {
        return idNatureAction;
    }

    public void setIdNatureAction(int idNatureAction) {
        this.idNatureAction = idNatureAction;
    }
}
