package de.fhms.sweng.event_management.entities;

import de.fhms.sweng.event_management.dto.EventTO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table (name="Events")
public class Event {

    @Id @GeneratedValue
    @Column (name="id")
    private int id;

    @ManyToOne (fetch =  FetchType.LAZY)
    @JoinColumn
    private BusinessUser businessUserId;

    private String name;
    private String description;
    private Date datetime;
    private int radius;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "location_id", referencedColumnName = "id")
    private Location location;

    @ManyToMany
    @JoinTable(
            name="event_preferences",
            joinColumns = @JoinColumn(name="event_id"),
            inverseJoinColumns = @JoinColumn(name="preference_id"))
    Set<Preference> preferences;

    //Contructor with description
    public Event(BusinessUser businessUser, String name, String description, Date datetime, int radius, double longitude, double latitude, Set<Preference> preferences) {

        this.businessUserId = businessUser;
        this.name = name;
        this.description = description;
        this.datetime = datetime;
        this.radius = radius;
        this.location = new Location(this, longitude,latitude);
        this.preferences = preferences;

    }

    //Constructor without description
    public Event(BusinessUser businessUser, String name, Date datetime, int radius, double longitude, double latitude, Set<Preference> preferences) {

        this.businessUserId = businessUser;
        this.name = name;
        this.datetime = datetime;
        this.radius = radius;
        this.location = new Location(this,longitude,latitude);
        this.preferences = preferences;

    }
    //Constructor form EventTO and BusinessUser
    public Event(BusinessUser businessUser, EventTO eventTO, Set<Preference> preferences) {
        this.id = eventTO.getId();
        this.businessUserId = businessUser;
        this.name = eventTO.getName();
        if (eventTO.getDescription() != null) {
            this.description = eventTO.getDescription();
        }
        this.datetime = eventTO.getDatetime();
        this.radius = eventTO.getRadius();
        this.location = new Location(this,eventTO.getLongitude(),eventTO.getLatitude());
        this.preferences = preferences;
    }

    //Getters and Setters

    public int getId() {
        return id;
    }

    public int getBusinessUserId() {
        return businessUserId.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getLongitude() {
        return location.getLongitude();
    }

    public void setLongitude(double longitude) {
        location.setLongitude(longitude);
    }

    public double getLatitude() {
        return location.getLatitude();
    }

    public void setLatitude(double latitude) {
        location.setLatitude(latitude);
    }

    public Set<Preference> getPreferences() {
        return this.preferences;
    }
}
