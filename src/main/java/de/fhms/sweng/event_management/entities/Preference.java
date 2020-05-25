package de.fhms.sweng.event_management.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="Preferences")
public class Preference {

    @Id @GeneratedValue
    @Column (name = "id")
    int id;

    private String preference;

    @ManyToMany(mappedBy = "preferences")
    Set<Event> events;

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
