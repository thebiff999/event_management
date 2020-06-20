package de.fhms.sweng.event_management.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotAuthorizedExceptionTest {

    @Test
    void throwWithMessage() {
        assertThrows(NotAuthorizedException.class, () -> {
            throw new NotAuthorizedException("test");
        });
    }

    @Test
    void throwWithoutMessage() {
        assertThrows(NotAuthorizedException.class, () -> {
            throw new NotAuthorizedException();
        });
    }

}
