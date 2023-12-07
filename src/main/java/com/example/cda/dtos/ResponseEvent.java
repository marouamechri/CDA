package com.example.cda.dtos;

import com.example.cda.modeles.Event;
import com.example.cda.modeles.NatureAction;
import com.example.cda.modeles.SubSubject;

public class ResponseEvent {
    private Event event;
    private String nameSpace;
    private String titleSubject;
    private  String titleSubSubject;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getTitleSubject() {
        return titleSubject;
    }

    public void setTitleSubject(String titleSubject) {
        this.titleSubject = titleSubject;
    }

    public String getTitleSubSubject() {
        return titleSubSubject;
    }

    public void setTitleSubSubject(String titleSubSubject) {
        this.titleSubSubject = titleSubSubject;
    }

    public ResponseEvent(Event event, String nameSpace, String titleSubject, String titleSubSubject) {
        this.event = event;
        this.nameSpace = nameSpace;
        this.titleSubject = titleSubject;
        this.titleSubSubject = titleSubSubject;
    }
}
