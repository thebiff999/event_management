package de.fhms.sweng.event_management.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="Preferences")
public class Preference {

    @Id @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    int id;

    private String preference;

    @ManyToMany(mappedBy = "preferences")
    Set<Event> events;

    //Empty Constructor
    public Preference() {}

    //Constructor
    public Preference(String preference) {
        this.preference = preference;
    }

    public int getId() {
        return this.id;
    }

    public String getPreference() {
        return this.preference;
    }

}
