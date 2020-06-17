package de.fhms.sweng.event_management.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * exception that is thrown when a user is authorized but not allowed to do a certain action
 * returns HTTP Response 403 FORBIDDEN
 * @author Dennis Heuermann
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotAllowedException extends RuntimeException{

    /**
     * constructor with a customizable message
     * @param message String that will be shown as the exception message
     */
    public NotAllowedException(String message) {
        super(message);
    }

    /**
     * empty constructor with a prefilled message
     */
    public NotAllowedException() {
        super("The user does not have the rights for this action");
    }

}
