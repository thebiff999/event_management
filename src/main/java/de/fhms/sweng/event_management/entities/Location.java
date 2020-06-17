package de.fhms.sweng.event_management.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * location entity that relates to a location in the database. Used to save the location of an event.
 * @author Dennis Heuermann
 */
@Entity
@Table (name="Locations")
public class Location {

    @Id @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    int id;

    private Double longitude;
    private Double latitude;

    @OneToOne(mappedBy = "location")
    private Event event;

    public Location (Event event, Double longitude, Double latitude) {

        this.event = event;
        this.longitude = longitude;
        this.latitude = latitude;

    }

    /**
     * empty constructor
     */
    public Location() {}

    //Override methods
    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (!(o instanceof Location)) { return false; }
        Location l = (Location) o;
        if (l.getLongitude().equals(this.longitude) && l.getLatitude().equals(this.latitude)) {
            return true;
        }
        else {
            return false;
        }
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
