package de.fhms.sweng.event_management.dto;

import de.fhms.sweng.event_management.entities.Event;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class EventTO {

    private int id;
    private int businessUserId;
    private String name;
    private String description;
    private Date datetime;



    private int radius;
    private double longitude;
    private double latitude;

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
    }

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

    public Date getDatetime() {
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

}
