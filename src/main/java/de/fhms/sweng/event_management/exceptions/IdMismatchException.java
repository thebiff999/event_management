package de.fhms.sweng.event_management.exceptions;

//TODO: Add HTTP Value
public class IdMismatchException extends RuntimeException {

    public IdMismatchException(String message) {
        super(message);
    }

    public IdMismatchException() {
        super("Id's don't match");
    }

}
