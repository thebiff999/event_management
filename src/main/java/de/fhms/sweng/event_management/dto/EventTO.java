package de.fhms.sweng.event_management.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import de.fhms.sweng.event_management.entities.Event;
import de.fhms.sweng.event_management.entities.Preference;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class EventTO implements Serializable {

    private int id;
    private int businessUserId;
    private String name;
    private String description;
    @JsonSerialize (using = LocalDateTimeSerializer.class)
    @JsonDeserialize (using = LocalDateTimeDeserializer.class)
    private LocalDateTime datetime;
    private int radius;
    private double longitude;
    private double latitude;
    private Set<Preference> preferences;


    //Empty Constructor
    public EventTO() {}

    //Constructor from Event Entitiy
    public EventTO (Event event) {
        this.id = event.getId();
        this.businessUserId = event.getBusinessUserId();
        this.name = event.getName();
        if (event.getDescription() != null) {
            this.description = event.getDescription();
        }
        this.datetime = event.getDatetime();
        this.radius = event.getRadius();
        this.longitude = event.getLongitude();
        this.latitude = event.getLatitude();


        if (event.hasPreferences()) {
            this.preferences = event.getPreferences();
        }

    }

    //Override methods
    @Override
    public String toString() {
        return "\n" + getClass().getSimpleName() + "\n"
                + "id: " + id + "\n"
                + "name: " + name + "\n"
                + "businessUserId: " + businessUserId+ "\n"
                + "description" + description + "\n"
                + "datetime: " + datetime + "\n"
                + "longitude :" + longitude + "\n"
                + "latitude: " + latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof EventTO)) return false;
        EventTO e = (EventTO )o;
        if (this.id == e.getId() && this.name == e.getName() && this.businessUserId == e.getBusinessUserId() && this.description == e.getDescription() && this.datetime == e.getDatetime() && this.longitude == e.longitude && this.latitude == e.latitude) return true;
        else return false;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public int getBusinessUserId() {
        return businessUserId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public int getRadius() {
        return radius;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public Set<Preference> getPreferences() {
        return preferences;
    }

    public void setPreferences(HashSet<Preference> preferences) {
        this.preferences = preferences;
    }


    //boolean methods
    public boolean hasPreferences() {
        if (this.preferences == null) { return false; }
        if (this.preferences.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean hasDescription() {
        if (this.description == null) { return false; }
        else { return true; }
    }

}
