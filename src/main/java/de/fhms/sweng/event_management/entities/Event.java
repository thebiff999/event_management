package de.fhms.sweng.event_management.entities;

import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;

@Entity
@Table (name="EVENT")
public class Event {

    @Id @GeneratedValue
    private int id;

    @ManyToOne
    private BusinessUser fk_business_user_id;

    private String name;
    private String description;
    private DateTimeFormat datetime;
    private Integer radius;
    private Double longitude;
    private Double lattittude;

    public Event() {

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

    public DateTimeFormat getDatetime() {
        return datetime;
    }

    public void setDatetime(DateTimeFormat datetime) {
        this.datetime = datetime;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLattittude() {
        return lattittude;
    }

    public void setLattittude(Double lattittude) {
        this.lattittude = lattittude;
    }




}
