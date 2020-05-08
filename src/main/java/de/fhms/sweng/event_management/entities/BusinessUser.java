package de.fhms.sweng.event_management.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name="Business_User")
public class BusinessUser {

    @Id
    private int id;

    private String name;

    @OneToMany (cascade=CascadeType.ALL, mappedBy="user")
    @JoinColumn (name="fk_business_user_id")
    private Set<Event> events = new HashSet<Event>();

}
