package de.fhms.sweng.event_management.entities;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table (name="Event")
public class Event {

    @Id @GeneratedValue
    private int id;

    @ManyToOne (fetch =  FetchType.LAZY)
    @JoinColumn
    private BusinessUser businessUserId;

    private String name;
    private String description;
    private Date datetime;
    private int radius;

    @OneToOne
    private Location location;

    //Contructor with description
    public Event(BusinessUser businessUser, String name, String description, Date datetime, int radius, double longitude, double latitude) {

        this.businessUserId = businessUser;
        this.name = name;
        this.description = description;
        this.datetime = datetime;
        this.radius = radius;
        this.location = new Location(this, longitude,latitude);

    }

    //Constructor without description
    public Event(BusinessUser businessUser, String name, Date datetime, int radius, double longitude, double latitude) {

        this.businessUserId = businessUser;
        this.name = name;
        this.datetime = datetime;
        this.radius = radius;
        this.location = new Location(this,longitude,latitude);

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
        return location.getLongitude()
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
}
