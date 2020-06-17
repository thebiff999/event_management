package de.fhms.sweng.event_management.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * exception that is thrown when a user is not authorized but tries to access a secured method
 * returns HTTP status 401 NOT AUTHORZIED
 * @author Dennis Heuermann
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotAuthorizedException extends RuntimeException {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * empty constructor with prefilled message
     */
    public NotAuthorizedException() {
        super("User not authorized");
        LOGGER.warn("User could not be authorized");
    }


    /**
     * constructor with a customizable message
     * @param message String that will be shown as the exception message
     */
    public NotAuthorizedException(String message) {
        super(message);
        LOGGER.warn("User could not be authorized");
    }
}
