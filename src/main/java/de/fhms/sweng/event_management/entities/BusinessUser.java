package de.fhms.sweng.event_management.entities;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name="Business_Users")
public class BusinessUser {

    @Id
    @Column (name = "id")
    private int id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "businessUserId")
    private Set<Event> events = new HashSet<Event>();


    //Empty Constructor
    public BusinessUser() {}

    //Getters and Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}