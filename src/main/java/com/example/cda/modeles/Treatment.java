package com.example.cda.modeles;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
public class Treatment extends Event {


    private Date dateFin;
    public Treatment(){
        super();
    }
    public Treatment(Event event, Date dateFin) {
        super(event.getDate(), event.isValidate(), event.getSubSubject(), event.getNatureAction());
        this.dateFin = dateFin;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
}
