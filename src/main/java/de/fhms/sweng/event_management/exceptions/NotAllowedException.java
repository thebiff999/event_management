package de.fhms.sweng.event_management.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotAllowedException extends RuntimeException{

    public NotAllowedException(String message) {
        super(message);
    }

    public NotAllowedException() {
        super("The user does not have the rights for this action");
    }

}
