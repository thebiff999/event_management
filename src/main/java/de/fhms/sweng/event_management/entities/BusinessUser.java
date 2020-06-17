package de.fhms.sweng.event_management.entities;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * BusinessUser entity that relates to a event user in the database
 * @author Dennis Heuermann
 */
@Entity
@Table (name="Business_Users")
public class BusinessUser {

    @Id
    @Column (name = "id")
    private int id;

    @Column (name = "first_name")
    private String firstName;

    @Column (name = "last_name")
    private String lastName;

    private String mail;

    private String role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "businessUserId")
    private Set<Event> events = new HashSet<Event>();


    /**
     * empty constructor which automaticlly sets the role 'EUSER' since this service only deals with users who create and own events
     */
    public BusinessUser() {
        this.role = "EUSER";
    }

    //Override methods
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (!(o instanceof BusinessUser)) { return false; }
        BusinessUser u = (BusinessUser) o;
        if (u.getId() == this.id && u.getMail() == this.mail && u.getFirstName() == this.firstName && u.getLastName() == this.lastName) {
            return true;
        }
        else {
            return false;
        }
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() { return lastName; }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public void setLastName(String name) { this.lastName = name; }

    public String getMail() { return mail; }

    public void setMail(String mail) { this.mail = mail;}

    public String getRole() { return this.role; }

}