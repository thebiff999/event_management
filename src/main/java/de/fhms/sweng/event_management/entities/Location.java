package de.fhms.sweng.event_management.entities;

import javax.persistence.*;

@Entity
@Table (name="Locations")
public class Location {

    @Id
    @GeneratedValue
    int id;

    private Double longitude;
    private Double latitude;

    @OneToOne(mappedBy = "location", cascade = CascadeType.ALL)
    @MapsId
    private Event event;

    public Location (Event event, Double longitude, Double latitude) {

        this.event = event;
        this.longitude = longitude;
        this.latitude = latitude;

    }

    //Getters and Setters
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
