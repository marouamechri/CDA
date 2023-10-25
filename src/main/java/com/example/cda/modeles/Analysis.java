package com.example.cda.modeles;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Analysis extends Event {
    @OneToMany(mappedBy = "resultAnalyse")
    private List<Document> results;
    public Analysis(){
        super();
    };
    public Analysis(Event event) {
        super(event.getDate(), event.isValidate(), event.getSubSubject(),
                event.getNatureAction());
    }

    public List<Document> getResults() {
        return results;
    }

    public void setResults(List<Document> results) {
        this.results = results;
    }
}
