package de.fhms.sweng.event_management.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * preference entity that relates to a preference in the database. preferences are mapped by events.
 * @author Dennis Heuermann
 */
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

    //Override methods
    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }

    @Override
    public boolean equals (Object o) {
        if (o == this) { return true; }
        if (!(o instanceof Preference)) { return false; }
        Preference p = (Preference) o;
        if (p.getId() == this.id && p.getValue() == this.value) {
            return true;
        }
        else {
            return false;
        }
    }

    //Getters and setters

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
