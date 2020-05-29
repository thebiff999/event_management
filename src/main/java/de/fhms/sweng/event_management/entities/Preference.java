package de.fhms.sweng.event_management.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties (ignoreUnknown = true)
@Entity
@Table(name="Preferences")
public class Preference {

    @Id @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    int id;

    private String value;

    @JsonIgnore
    @ManyToMany (mappedBy = "preferences")
    private Set<Event> events;

    //Empty Constructor
    public Preference() {
        events = new HashSet<Event>();
    }

    //Constructor
    public Preference(String value) {
        this.value = value;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) { this.id = id; }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) { this.value = value; }

    public Set<Event> getEvents() { return this.events; }

    public void setEvents(Set<Event> events) { this.events = events; }

    public void addEvent(Event event) { events.add(event); }

}
