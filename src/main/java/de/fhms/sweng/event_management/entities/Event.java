package de.fhms.sweng.event_management.entities;

import de.fhms.sweng.event_management.dto.EventTO;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table (name="Events")
public class Event {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id")
    private int id;

    @ManyToOne
    @JoinColumn (name="business_user_id")
    private BusinessUser businessUserId;

    private String name;
    private String description;
    private LocalDateTime datetime;
    private int radius;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "location_id", referencedColumnName = "id")
    private Location location;

    @ManyToMany (cascade = CascadeType.MERGE)
    @JoinTable(
            name="event_preferences",
            joinColumns = @JoinColumn(name="event_id"),
            inverseJoinColumns = @JoinColumn(name="preference_id"))
    Set<Preference> preferences;

    //Standard-Constructor
    public Event() {
        preferences = new HashSet<Preference>();
        location = new Location();
    }

    //Contructor with description
    public Event(BusinessUser businessUser, String name, String description, LocalDateTime datetime, int radius, double longitude, double latitude, Set<Preference> preferences) {

        this.businessUserId = businessUser;
        this.name = name;
        this.description = description;
        this.datetime = datetime;
        this.radius = radius;
        this.location = new Location(this, longitude,latitude);
        this.preferences = preferences;

    }

    //Constructor without description
    public Event(BusinessUser businessUser, String name, LocalDateTime datetime, int radius, double longitude, double latitude, Set<Preference> preferences) {

        this.businessUserId = businessUser;
        this.name = name;
        this.datetime = datetime;
        this.radius = radius;
        this.location = new Location(this,longitude,latitude);
        this.preferences = preferences;

    }

    public boolean hasPreferences() {
        if (this.preferences == null) { return false; }
        if (this.preferences.isEmpty()) return false;
        else return true;
    }

    //Override methods
    @Override
    public String toString() {
        return "\n" + getClass().getSimpleName() + "\n"
                + "id: " + id + "\n"
                + "name: " + name + "\n" +
                "businessUserId: " + businessUserId.getId() + "\n" +
                "description" + description + "\n" +
                "datetime: " + datetime + "\n" +
                "longitude :" + location.getLongitude() + "\n" +
                "latitude: " + location.getLatitude();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, businessUserId, name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (!(o instanceof Event)) { return false; }
        Event e = (Event) o;
        if (e.getId() == this.id && e.getName() == this.name && e.getDescription() == this.description && e.getLocation().equals(this.location) && e.getDatetime().equals(this.datetime) && e.getPreferences().equals(this.preferences) && e.getRadius() == this.radius && e.getBusinessUserId() == this.businessUserId.getId()) {
            return true;
        }
        else {
            return false;
        }
    }

    //other methods
    public void addPreference(Preference preference) {
        preferences.add(preference);
    }

    //Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id;}

    public int getBusinessUserId() {
        return businessUserId.getId();
    }

    public void setBusinessUserId(BusinessUser businessUser) {this.businessUserId = businessUser;}

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

    public LocalDateTime getDatetime() { return datetime; }

    public void setDatetime(LocalDateTime datetime) {
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

    public Location getLocation() { return this.location; }

    public void setLocation(Location location) { this.location = location; }

    public Set<Preference> getPreferences() {
        return this.preferences;
    }

    public void setPreferences(Set<Preference> preferences) {
        this.preferences = preferences;
        }

}
