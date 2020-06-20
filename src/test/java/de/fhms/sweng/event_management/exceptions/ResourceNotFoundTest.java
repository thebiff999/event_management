package de.fhms.sweng.event_management.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceNotFoundTest {

    @Test
    void throwWithMessage() {
        assertThrows(ResourceNotFoundException.class, () -> {
            throw new ResourceNotFoundException("test");
        });
    }

    @Test
    void throwWithoutMessage() {
        assertThrows(ResourceNotFoundException.class, () -> {
            throw new ResourceNotFoundException();
        });
    }

}
