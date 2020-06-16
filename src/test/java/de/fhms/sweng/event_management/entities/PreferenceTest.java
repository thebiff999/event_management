package de.fhms.sweng.event_management.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PreferenceTest {

    private Preference preference;
    private Event event;
    private HashSet<Event> eventSet;
    private Preference equalPreference;
    private Preference differentPreference;

    @BeforeEach
    void setUp() {
        event = new Event();
        event.setId(1);
        event.setDescription("description");
        event.setName("event");
        event.setRadius(5);

        eventSet = new HashSet<Event>();
        eventSet.add(event);

        preference = new Preference();
        preference.setId(1);
        preference.setValue("Value");
        preference.setEvents(eventSet);

        equalPreference = new Preference();
        equalPreference.setId(1);
        equalPreference.setValue("Value");

        differentPreference = new Preference();
        differentPreference.setId(2);
        differentPreference.setValue("different");
    }

    @Test
    void testHashCodeTrue() {
        assertEquals(preference.hashCode(), equalPreference.hashCode());
    }

    @Test
    void testHashCodeFalse() {
        assertNotEquals(preference.hashCode(), differentPreference.hashCode());
    }

    @Test
    void testHashCodeSelf() {
        assertEquals(preference.hashCode(), preference.hashCode());
    }

    @Test
    void testEqualsTrue() {
        assertTrue(equalPreference.equals(preference));
    }

    @Test
    void testEqualsFalse() {
        assertFalse(differentPreference.equals(preference));
    }

    @Test
    void testEqualsSelf() {
        assertTrue(preference.equals(preference));
    }

    @Test
    void getId() {
        assertEquals(1, preference.getId());
    }

    @Test
    void setId() {
        preference.setId(2);
        assertEquals(2, preference.getId());
    }

    @Test
    void getValue() {
        assertEquals("Value", preference.getValue());
    }

    @Test
    void setValue() {
        preference.setValue("Sport");
        assertEquals("Sport", preference.getValue());
    }

    @Test
    void getEvents() {
        assertEquals(eventSet, preference.getEvents());
    }

    @Test
    void setEvents() {
        Event e = new Event();
        eventSet.add(e);
        preference.setEvents(eventSet);
        assertEquals(eventSet, preference.getEvents());
    }

    @Test
    void addEvent() {
        Event e = new Event();
        eventSet.add(e);
        preference.addEvent(e);
        assertEquals(eventSet, preference.getEvents());
    }
}