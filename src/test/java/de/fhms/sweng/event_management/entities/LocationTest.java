package de.fhms.sweng.event_management.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    private Location location;

    @BeforeEach
    void setUp() {
        location = new Location();
        location.setLongitude(50.00);
        location.setLatitude(60.00);
    }

    @Test
    void testHashCodeTrue() {
        Location l = new Location();
        l.setLongitude(50.00);
        l.setLatitude(60.00);
        assertEquals(l.hashCode(), location.hashCode());
    }

    @Test
    void testHashCodeFalse() {
        Location l = new Location();
        l.setLongitude(60.00);
        l.setLatitude(50.00);
        assertNotEquals(l.hashCode(), location.hashCode());
    }

    @Test
    void getLongitude() {
        assertEquals(Double.valueOf(50.00), location.getLongitude());
    }

    @Test
    void setLongitude() {
        location.setLongitude(70.00);
        assertEquals(Double.valueOf(70.00), location.getLongitude());
    }

    @Test
    void getLatitude() {
        assertEquals(Double.valueOf(60.00), location.getLatitude());
    }

    @Test
    void setLatitude() {
        location.setLatitude(80.00);
        assertEquals(Double.valueOf(80.00), location.getLatitude());
    }
}