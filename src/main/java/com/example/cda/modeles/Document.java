package com.example.cda.modeles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
public class Document {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private String type;
    @Column(name = "data", columnDefinition = "LONGBLOB")
    @Lob
    private byte[] data;

    @ManyToMany
    private List<Event> events;
    @JsonIgnore
    @ManyToMany
    private List<Task> tasks;

    public Document(){};
    public Document(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
    public void setEvents(List<Event> events) {
        this.events = events;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
