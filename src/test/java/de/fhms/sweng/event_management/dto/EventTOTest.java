package de.fhms.sweng.event_management.dto;

import de.fhms.sweng.event_management.entities.Preference;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EventTOTest {

    private EventTO event;
    private Preference preference;
    private LocalDateTime time;
    private HashSet<Preference> preferenceSet;

    @BeforeEach
    void setUp() {
        time = LocalDateTime.now();
        preference = new Preference();
        preference.setId(1);
        preference.setValue("Music");
        preferenceSet = new HashSet<Preference>();
        preferenceSet.add(preference);

        event = new EventTO(1, 1, "event", "description", time, 5, 60.00, 70.00, preferenceSet);
    }

    @Test
    void testToString() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void getId() {
        assertEquals(1, event.getId());
    }

    @Test
    void getBusinessUserId() {
        assertEquals(1, event.getBusinessUserId());
    }

    @Test
    void getName() {
        assertEquals("event", event.getName());
    }

    @Test
    void getDescription() {
        assertEquals("description", event.getDescription());
    }

    @Test
    void getDatetime() {
        assertEquals(time, event.getDatetime());
    }

    @Test
    void getRadius() {
        assertEquals(5, event.getRadius());
    }

    @Test
    void getLongitude() {
        assertEquals(60.00, event.getLongitude());
    }

    @Test
    void getLatitude() {
        assertEquals(70.00, event.getLatitude());
    }

    @Test
    void getPreferences() {
        assertEquals(preferenceSet, event.getPreferences());
    }

    @Test
    void setPreferences() {
        Preference p = new Preference();
        p.setId(2);
        p.setValue("Sport");
        preferenceSet.add(p);
        event.setPreferences(preferenceSet);
        assertEquals(preferenceSet, event.getPreferences());
    }

    @Test
    void hasPreferencesTrue() {
        assertTrue(event.hasPreferences());
    }

    @Test
    void hasPreferencesFalse() {
        event.setPreferences(null);
        assertFalse(event.hasPreferences());
    }

    @Test
    void hasDescriptionTrue() {
        assertTrue(event.hasDescription());
    }

    @Test
    void hasDescriptionFalse() {
        EventTO event2 = new EventTO(1, 1, "event", null, time, 5, 60.00, 70.00, preferenceSet);
        assertFalse(event.hasDescription());
    }
}