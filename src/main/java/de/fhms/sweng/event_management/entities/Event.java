package de.fhms.sweng.event_management.entities;

import java.util.Date;
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
    private Date datetime;
    private Integer radius;
    private Double longitude;
    private Double lattittude;

    public Event() {

    }


}
