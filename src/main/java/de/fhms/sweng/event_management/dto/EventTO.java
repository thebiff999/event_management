package de.fhms.sweng.event_management.dto;

import de.fhms.sweng.event_management.entities.Event;
import org.springframework.format.annotation.DateTimeFormat;

public class EventTO {

    private int id;
    private int businessUserId;
    private String name;
    private String description;
    private DateTimeFormat datetime;
    private int radius;
    private double longitude;
    private double latitude;

    public EventTO (Event event) {
        this.id = event.getId();
        this.businessUserId = event.getBusinessUserId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.datetime = event.getDatetime();
        this.radius = event.getRadius();
        this.longitude = event.getLongitude();
        this.latitude = event.getLatitude();
    }

}
