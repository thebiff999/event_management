package de.fhms.sweng.event_management.dto;

import java.io.Serializable;
/**
 * Simple Data Transfer Object used to retrieve a JSON web token from the Login service
 * @author Dennis Heuermann
 */
public class UserTO implements Serializable {

    private String email;
    private String password;

    /**
     * empty constructor
     */
    public UserTO() {}

    /**
     * constructor with parameters
     * @param email email of the login object
     * @param password password of the login object
     */
    public UserTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setEmail(String email) { this.email = email;}

    public String getEmail() { return this.email; }

    public void setPassword(String password) { this.password = password; }

    public String getPassword() { return this.password; }

}
