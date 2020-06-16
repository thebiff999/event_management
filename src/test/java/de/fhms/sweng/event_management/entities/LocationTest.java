package de.fhms.sweng.event_management.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    private Location location;
    private Location equalLocation;
    private Location differentLocation;

    @BeforeEach
    void setUp() {
        location = new Location();
        location.setLongitude(50.00);
        location.setLatitude(60.00);

        equalLocation = new Location();
        equalLocation.setLongitude(50.00);
        equalLocation.setLatitude(60.00);

        differentLocation = new Location();
        differentLocation.setLongitude(60.00);
        differentLocation.setLatitude(50.00);
    }

    @Test
    void testEqualsTrue() {
        assertTrue(equalLocation.equals(location));
    }

    @Test
    void testEqualsFalse() {
        assertFalse(differentLocation.equals(location));
    }

    @Test
    void testEqualsSelf() {
        assertTrue(location.equals(location));
    }

    @Test
    void testHashCodeSelf() {
        assertEquals(location.hashCode(), location.hashCode());
    }

    @Test
    void testHashCodeTrue() {
        assertEquals(location.hashCode(), equalLocation.hashCode());
    }

    @Test
    void testHashCodeFalse() {
        assertNotEquals(location.hashCode(), differentLocation.hashCode());
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