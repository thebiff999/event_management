package de.fhms.sweng.event_management.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotAuthorizedException extends RuntimeException {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public NotAuthorizedException() {
        super("User not authorized");
        LOGGER.warn("User could not be authorized");
    }

    public NotAuthorizedException(String message) {
        super(message);
        LOGGER.warn("User could not be authorized");
    }
}
