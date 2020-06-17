package de.fhms.sweng.event_management.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * exception that is thrown when a certain resource is requested but can not be found
 * returns HTTP status 404 NOT FOUND
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException() { super("Authentication failed"); }

}
