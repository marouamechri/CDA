package com.example.cda.modeles;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Hospitalization extends Event {
    private Date dateEnd;
    @ManyToOne
    Hospital hospital;
    public Hospitalization(){
        super();
    }
    public Hospitalization(Event event, Date dateEnd, Hospital hospital) {
        super(event.getDate(),event.isValidate(), event.getSubSubject(), event.getNatureAction());
        this.dateEnd = dateEnd;
        this.hospital = hospital;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
}
