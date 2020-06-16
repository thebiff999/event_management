package de.fhms.sweng.event_management.services;

import de.fhms.sweng.event_management.dto.BusinessUserTO;
import de.fhms.sweng.event_management.dto.EventTO;
import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.entities.Event;
import de.fhms.sweng.event_management.entities.Location;
import de.fhms.sweng.event_management.entities.Preference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class MapperServiceTest {

    private Event event1;
    private Event event2;
    private HashSet<Event> eventSet;
    private BusinessUserTO userTO;
    private BusinessUser user1;
    private BusinessUser user2;
    private LocalDateTime time1;
    private LocalDateTime time2;
    private Location location1;
    private Location location2;
    private HashSet<Preference> preferenceSet1;
    private HashSet<Preference> preferenceSet2;
    private EventTO eventTO1;
    private EventTO eventTO2;
    private HashSet<EventTO> eventTOSet;
    private MapperService mapperService;

    @BeforeEach
    public void setUp() {

        mapperService = new MapperService();

        //Setting up LocalDateTime variables
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String datetime1 = "2020-06-06 20:00";
        time1 = LocalDateTime.parse(datetime1, formatter);
        String datetime2 = "2020-07-07 11:00";
        time2 = LocalDateTime.parse(datetime2, formatter);

        //Setting up BusinessUsers
        userTO = new BusinessUserTO();
        userTO.setId(1);
        userTO.setEmail("max@mustermann.de");
        userTO.setFirstName("Max");
        userTO.setLastName("Mustermann");

        user1 = new BusinessUser();
        user1.setId(1);
        user1.setMail("max@mustermann.de");
        user1.setFirstName("Max");
        user1.setLastName("Mustermann");

        user2 = new BusinessUser();
        user2.setId(2);
        user2.setMail("moritz@mustermann.de");
        user2.setFirstName("Moritz");
        user2.setLastName("Mustermann");

        //Setting up Locations
        location1 = new Location();
        location1.setLongitude(10.00);
        location1.setLatitude(10.00);

        location2 = new Location();
        location2.setLongitude(20.00);
        location2.setLatitude(20.00);

        //Setting up PreferenceSets (empty)
        preferenceSet1 = new HashSet<Preference>();
        preferenceSet2 = new HashSet<Preference>();

        //Setting up the events
        event1 = new Event();
        event1.setId(1);
        event1.setName("Test Event 1");
        event1.setDescription("Description 1");
        event1.setRadius(1);
        event1.setPreferences(preferenceSet1);
        event1.setLocation(location1);
        event1.setDatetime(time1);
        event1.setBusinessUserId(user1);

        event2 = new Event();
        event2.setId(2);
        event2.setName("Test Event 2");
        event2.setDescription("Description 2");
        event2.setRadius(2);
        event2.setPreferences(preferenceSet2);
        event2.setLocation(location2);
        event2.setDatetime(time2);
        event2.setBusinessUserId(user2);

        eventSet = new HashSet<Event>();
        eventSet.add(event1);
        eventSet.add(event2);

        //Setting up the eventTOs
        eventTO1 = new EventTO(event1);
        eventTO2 = new EventTO(event2);
        eventTOSet = new HashSet<EventTO>();
        eventTOSet.add(eventTO1);
        eventTOSet.add(eventTO2);
    }

    @Test
    void ShouldConvertEvent() {
        EventTO convertedEvent = mapperService.convertToEventTO(event1);
        assertEquals(eventTO1, convertedEvent);
    }

    @Test
    void ShouldConvertEventSet() {
        Set<EventTO> convertedEventSet = mapperService.convertToEventTO(eventSet);
        assertEquals(eventTOSet, convertedEventSet);
    }

    @Test
    void shouldConvertBusinessUserTO() {
        BusinessUser convertedUser = mapperService.convertToUser(userTO);
        assertEquals(user1, convertedUser);
    }

}
