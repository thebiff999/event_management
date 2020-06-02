package de.fhms.sweng.event_management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.fhms.sweng.event_management.entities.Preference;
import java.io.Serializable;

@JsonIgnoreProperties (ignoreUnknown = true)
public class BusinessUserTO implements Serializable {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    public BusinessUserTO() {
    }

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
