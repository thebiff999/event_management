package de.fhms.sweng.event_management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.fhms.sweng.event_management.entities.Preference;
import java.io.Serializable;
import java.util.Objects;

/**
 * data transfer object of BusinessUser entity
 * @author Dennis Heuermann
 */
@JsonIgnoreProperties (ignoreUnknown = true)
public class BusinessUserTO implements Serializable {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    /**
     * empty constructor
     */
    public BusinessUserTO() {
    }

    //Override methods
    @Override
    public String toString() {
        return getClass().getSimpleName() + "\n"
                + "id: " + id + "\n"
                + "firstName: " + firstName + "\n"
                + "lastName: " + lastName + "\n"
                + "email: " + email + "\n";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email);
    }

    //Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
