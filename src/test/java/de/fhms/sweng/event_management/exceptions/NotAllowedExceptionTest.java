package de.fhms.sweng.event_management.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotAllowedExceptionTest {

    @Test
    void throwWithMessage() {
        assertThrows(NotAllowedException.class, () -> {
            throw new NotAllowedException("test");
        });
    }

    @Test
    void throwWithoutMessage() {
        assertThrows(NotAllowedException.class, () -> {
            throw new NotAllowedException();
        });
    }

}
