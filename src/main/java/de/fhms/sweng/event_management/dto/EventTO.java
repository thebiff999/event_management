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

/**
 * data transfer Object of Event entitiy
 * @author Dennis Heuermann
 */
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


    /**
     * empty constructor
     */
    public EventTO() {

    }

    /**
     * constructs an EventTO Object from an Event entitiy
     * @param event event from which the DTO will be constructed
     */
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

    /**
     * constructor with content
     * @param id id of EventTO
     * @param businessUserId id of the owner
     * @param name name of the event
     * @param description description of the event
     * @param datetime date and time of the event
     * @param radius radius of the event
     * @param longitude longitude position of the event
     * @param latitude latitude position of the event
     * @param preferences set of preferences the event maps
     */
    public EventTO (int id, int businessUserId, String name, String description, LocalDateTime datetime, int radius, double longitude, double latitude, Set<Preference> preferences) {
        this. id = id;
        this.businessUserId = businessUserId;
        this.name = name;
        this.description = description;
        this.datetime = datetime;
        this.radius = radius;
        this.longitude = longitude;
        this.latitude = latitude;
        this.preferences = preferences;
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

    @Override
    public int hashCode() {
        return Objects.hash(id, businessUserId, name, longitude, latitude);
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public int getBusinessUserId() {
        return businessUserId;
    }

    public void setBusinessUserId(int businessUserId) { this.businessUserId = businessUserId; }

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
