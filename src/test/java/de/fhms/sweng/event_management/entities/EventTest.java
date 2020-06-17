package de.fhms.sweng.event_management.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    private Event event;
    private BusinessUser user;
    private String datetime;
    private Preference preference;
    private HashSet<Preference> preferenceSet;
    private LocalDateTime time;
    private Location location;
    private Event event2;
    private Event event3;

    @BeforeEach
    void setUp() {

        user = new BusinessUser();
        user.setId(1);
        user.setFirstName("first");
        user.setLastName("last");
        user.setMail("first@last.de");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        datetime = "2020-06-06 20:00";
        time = LocalDateTime.parse(datetime, formatter);

        preference = new Preference();
        preference.setId(1);
        preference.setValue("Music");
        preferenceSet = new HashSet<Preference>();
        preferenceSet.add(preference);

        location = new Location();
        location.setLongitude(60.00);
        location.setLatitude(60.00);

        event = new Event();
        event.setId(1);
        event.setBusinessUserId(user);
        event.setDescription("description");
        event.setName("event");
        event.setLocation(location);
        event.setRadius(5);
        event.setDatetime(time);
        event.setPreferences(preferenceSet);

        event2 = new Event();
        event2.setId(1);
        event2.setBusinessUserId(user);
        event2.setDescription("description");
        event2.setName("event");
        event2.setLocation(location);
        event2.setRadius(5);
        event2.setDatetime(time);
        event2.setPreferences(preferenceSet);

        event3 = new Event();
        event3.setId(2);
        event3.setBusinessUserId(user);
        event3.setDescription("description2");
        event3.setName("event2");
        event3.setLocation(location);
        event3.setRadius(5);
        event3.setDatetime(time);
        event3.setPreferences(preferenceSet);
    }


    @Test
    void ConstructorWithDescription() {
        Event newEvent = new Event(user, "name", "description", time, 5, 10.00, 20.00, preferenceSet);
        assertEquals(1, newEvent.getBusinessUserId());
        assertEquals("name", newEvent.getName());
        assertEquals("description", newEvent.getDescription());
        assertEquals(time, event.getDatetime());
        assertEquals(5, newEvent.getRadius());
        assertEquals(10.00, newEvent.getLongitude());
        assertEquals(20.00, newEvent.getLatitude());
        assertEquals(preferenceSet, newEvent.getPreferences());
    }

    @Test
    void ConstructorWithoutDescription() {
        Event newEvent = new Event(user, "name", time, 5, 10.00, 20.00, preferenceSet);
        assertEquals(1, newEvent.getBusinessUserId());
        assertEquals("name", newEvent.getName());
        assertEquals(null, newEvent.getDescription());
        assertEquals(time, event.getDatetime());
        assertEquals(5, newEvent.getRadius());
        assertEquals(10.00, newEvent.getLongitude());
        assertEquals(20.00, newEvent.getLatitude());
        assertEquals(preferenceSet, newEvent.getPreferences());
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
    void testEqualsTrue() {
        assertTrue(event.equals(event2));
    }

    @Test
    void testEqualsFalse() {
        assertFalse(event.equals(event3));
    }

    @Test
    void testEqualsSelf() {
        assertTrue(event.equals(event));
    }

    @Test
    void testHashCodeSelf() {
        assertEquals(event.hashCode(), event.hashCode());
    }

    @Test
    void testHashCodeTrue() {
        assertEquals(event.hashCode(), event2.hashCode());
    }

    @Test
    void testHashCodeFalse() {
        assertNotEquals(event.hashCode(), event3.hashCode());
    }

    @Test
    void addPreference() {
        Preference p = new Preference();
        p.setId(2);
        p.setValue("Cars");
        event.addPreference(p);
        preferenceSet.add(p);
        assertEquals(preferenceSet, event.getPreferences());
    }

    @Test
    void getId() {
        assertEquals(1, event.getId());
    }

    @Test
    void setId() {
        event.setId(2);
        assertEquals(2, event.getId());
    }

    @Test
    void getBusinessUserId() {
        assertEquals(1, event.getBusinessUserId());
    }

    @Test
    void setBusinessUserId() {
        BusinessUser user2 = new BusinessUser();
        user2.setFirstName("first2");
        user2.setLastName("last2");
        user2.setMail("first2@last2.de");
        user2.setId(2);

        event.setBusinessUserId(user2);
        assertEquals(2, event.getBusinessUserId());
    }

    @Test
    void getName() {
        assertEquals("event", event.getName());
    }

    @Test
    void setName() {
        event.setName("different");
        assertEquals("different", event.getName());
    }

    @Test
    void getDescription() {
        assertEquals("description", event.getDescription());
    }

    @Test
    void setDescription() {
        event.setDescription("different");
        assertEquals("different", event.getDescription());
    }

    @Test
    void getDatetime() {
        assertEquals(time, event.getDatetime());
    }

    @Test
    void setDatetime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        datetime = "2020-06-06 20:00";
        time = LocalDateTime.parse(datetime, formatter);

        event.setDatetime(time);
        assertEquals(time, event.getDatetime());
    }

    @Test
    void getRadius() {
        assertEquals(5, event.getRadius());
    }

    @Test
    void setRadius() {
        event.setRadius(6);
        assertEquals(6, event.getRadius());
    }

    @Test
    void getLongitude() {
        assertEquals(60.00, event.getLongitude());
    }

    @Test
    void setLongitude() {
        event.setLongitude(70.00);
        assertEquals(70.00, event.getLongitude());
    }

    @Test
    void getLatitude() {
        assertEquals(60.00, event.getLatitude());
    }

    @Test
    void setLatitude() {
        event.setLatitude(80.00);
        assertEquals(80.00, event.getLatitude());
    }

    @Test
    void getLocation() {
        assertEquals(location, event.getLocation());
    }

    @Test
    void setLocation() {
        Location l = new Location();
        l.setLongitude(10.00);
        l.setLatitude(20.00);
        event.setLocation(l);
        assertEquals(l, event.getLocation());
    }

    @Test
    void getPreferences() {
        assertEquals(preferenceSet, event.getPreferences());
    }

    @Test
    void setPreferences() {
        Preference p = new Preference();
        p.setId(3);
        p.setValue("Misc");
        preferenceSet.add(p);
        event.setPreferences(preferenceSet);
        assertEquals(preferenceSet, event.getPreferences());
    }
}