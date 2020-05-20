package de.fhms.sweng.event_management.entities;

import javax.persistence.*;

@Entity
@Table (name="Location")
public class Location {

    private Double longitude;
    private Double latitude;

    @OneToOne
    @MapsId
    private Event event;

    public Location (Event event, Double longitude, Double latitude) {

        this.event = event;
        this.longitude = longitude;
        this.latitude = latitude;

    }

}
