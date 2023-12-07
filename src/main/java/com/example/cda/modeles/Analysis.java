package com.example.cda.modeles;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Analysis extends Event {
    public Analysis(){
        super();
    };
    public Analysis(Event event) {
        super(event.getDate(), event.isValidate(), event.getSubSubject(),
                event.getNatureAction());
    }

}
