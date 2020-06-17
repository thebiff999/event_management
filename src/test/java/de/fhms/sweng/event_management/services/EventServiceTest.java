package de.fhms.sweng.event_management.services;


import de.fhms.sweng.event_management.dto.BusinessUserTO;
import de.fhms.sweng.event_management.dto.EventTO;
import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.entities.Event;
import de.fhms.sweng.event_management.entities.Location;
import de.fhms.sweng.event_management.entities.Preference;
import de.fhms.sweng.event_management.exceptions.ResourceNotFoundException;
import de.fhms.sweng.event_management.producer.EventProducer;
import de.fhms.sweng.event_management.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    private Event event;
    private Event event2;
    private Event event3;
    private EventTO eventTO;
    private EventTO eventTO2;
    private EventTO eventTO3;
    private LocalDateTime time;
    private BusinessUserTO userTO;
    private BusinessUser user;
    private Location location;
    private Location location2;
    private Preference preference;
    private Preference preference2;
    private Set<Preference> preferenceSet;
    private Set<Preference> preferenceSet2;
    private EventService eventService;
    private HashSet<Event> events;
    private HashSet<EventTO> eventTOs;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private BusinessUserService businessUserService;

    @Mock
    private PreferenceService preferenceService;

    @Mock
    private MapperService mapperService;

    @Mock
    private EventProducer eventProducer;

    @BeforeEach
    public void setUp() {

        eventService = new EventService(eventRepository, businessUserService, preferenceService, mapperService, eventProducer);

        //Setting up LocalDateTime variables
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String datetime1 = "2020-06-06 20:00";
        time = LocalDateTime.parse(datetime1, formatter);

        //Setting up BusinessUsers
        userTO = new BusinessUserTO();
        userTO.setId(1);
        userTO.setEmail("max@mustermann.de");
        userTO.setFirstName("Max");
        userTO.setLastName("Mustermann");

        user = new BusinessUser();
        user.setId(1);
        user.setMail("max@mustermann.de");
        user.setFirstName("Max");
        user.setLastName("Mustermann");

        //Setting up Locations
        location = new Location();
        location.setLongitude(10.00);
        location.setLatitude(10.00);

        location2 = new Location();
        location2.setLatitude(20.00);
        location2.setLongitude(20.00);

        //Setting up PreferenceSets
        preference = new Preference();
        preference.setValue("value");
        preference.setId(1);
        preferenceSet = new HashSet<Preference>();
        preferenceSet.add(preference);

        preference2 = new Preference();
        preference2.setValue("value2");
        preference2.setId(2);
        preferenceSet2 = new HashSet<Preference>();
        preferenceSet2.add(preference);
        preferenceSet2.add(preference2);


        //Setting up the events
        event = new Event();
        event.setId(0);
        event.setName("Test Event 1");
        event.setDescription("Description 1");
        event.setRadius(1);
        event.setPreferences(preferenceSet);
        event.setLocation(location);
        event.setDatetime(time);
        event.setBusinessUserId(user);

        event2 = new Event();
        event2.setId(0);
        event2.setName("Test Event 2");
        event2.setDescription("Description 2");
        event2.setRadius(2);
        event2.setPreferences(preferenceSet);
        event2.setLocation(location);
        event2.setDatetime(time);
        event2.setBusinessUserId(user);

        event3 = new Event();
        event3.setId(0);
        event3.setName("TEst Event 3");
        event3.setDescription(("Description 3"));
        event3.setRadius(3);
        event3.setPreferences(preferenceSet2);
        event3.setLocation(location);
        event3.setDatetime(time);
        event3.setBusinessUserId(user);

        eventTO = new EventTO(event);
        eventTO2 = new EventTO(event2);
        eventTO3 = new EventTO(event3);

        events = new HashSet<Event>();
        events.add(event);

        eventTOs = new HashSet<EventTO>();
        eventTOs.add(eventTO);

    }

    @Test
    void shouldCreateEvent() {
        given(businessUserService.getBusinessUser(1)).willReturn(user);
        given(preferenceService.getPrefernceByValue("value")).willReturn(Optional.of(preference));
        given(eventRepository.save(event)).willReturn(event);
        given(mapperService.convertToEventTO(event)).willReturn(eventTO);
        doNothing().when(eventProducer).sendNewEvent(eventTO);
        EventTO returnedEvent = eventService.createEvent(eventTO);
        assertEquals(eventTO, returnedEvent);

    }

    @Test
    void shouldUpdateEvent() {
        given(eventRepository.findById(0)).willReturn(Optional.of(event));
        given(mapperService.convertToEventTO(event)).willReturn(eventTO);
        given(eventRepository.save((event))).willReturn(event2);
        given(mapperService.convertToEventTO(event2)).willReturn(eventTO2);
        EventTO returnedEvent = eventService.updateEvent(0, eventTO2);
        assertEquals(eventTO2, returnedEvent);
    }

    @Test
    void shouldUpdateWithNewPreferenceAndLocation() {
        given(eventRepository.findById(0)).willReturn(Optional.of(event));
        given(mapperService.convertToEventTO(event)).willReturn(eventTO);
        given(preferenceService.getPrefernceByValue("value")).willReturn(Optional.of(preference));
        given(preferenceService.getPrefernceByValue("value2")).willReturn(Optional.of(preference2));
        given(eventRepository.save((event))).willReturn(event3);
        given(mapperService.convertToEventTO(event3)).willReturn(eventTO3);
        EventTO returnedEvent = eventService.updateEvent(0, eventTO3);
        assertEquals(eventTO3, returnedEvent);
    }

    @Test
    void shouldNotUpdateEvent() {
        given(eventRepository.findById(0)).willReturn(Optional.of(event));
        given(mapperService.convertToEventTO(event)).willReturn(eventTO);
        EventTO returnedEvent = eventService.updateEvent(0, eventTO);
        assertEquals(eventTO, returnedEvent);
    }

    @Test
    void shouldDeleteEvent() {
        given(eventRepository.findById(0)).willReturn(Optional.of(event));
        given(mapperService.convertToEventTO(event)).willReturn(eventTO);
        eventService.deleteEvent(0);
        verify(eventRepository).deleteById(0);
    }

    @Test
    void shouldNotDeleteEvent() {
        given(eventRepository.findById(5)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            eventService.deleteEvent(5);
        });
    }

    @Test
    void shouldGetAllEvents() {
        given(eventRepository.findAll()).willReturn(events);
        given(mapperService.convertToEventTO(events)).willReturn(eventTOs);
        HashSet<EventTO> returnedSet = (HashSet<EventTO>) eventService.getEvents();
        assertEquals(eventTOs, returnedSet);
    }

    @Test
    void shouldGetEventTOById() {
        given(eventRepository.findById(0)).willReturn(Optional.of(event));
        EventTO returnedEvent = eventService.getEventTOById(0);
        assertEquals(eventTO, returnedEvent);
    }


    @Test
    public void shouldNotGetEventTOById() {
        given(eventRepository.findById(5)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            eventService.getEventById(5);
        });
    }

    @Test
    void shouldGetEventById() {
        given(eventRepository.findById(0)).willReturn(Optional.of(event));
        Event returnedEvent = eventService.getEventById(0);
        assertEquals(event, returnedEvent);
    }

    @Test
    void shouldNotGetEventById() {
        given(eventRepository.findById(5)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            eventService.getEventById(5);
        });
    }

    @Test
    void shouldGetEventByName() {
        given(eventRepository.findByName("value")).willReturn(events);
        given(mapperService.convertToEventTO(events)).willReturn(eventTOs);
        HashSet<EventTO> returnedSet = (HashSet<EventTO>) eventService.getEventByName("value");
        assertEquals(eventTOs, returnedSet);
    }

    @Test
    void shouldNotGetEventByName() {
        HashSet<Event> eventHashSet = new HashSet<Event>();
        given(eventRepository.findByName("diesdas")).willReturn(eventHashSet);
        assertThrows(ResourceNotFoundException.class, () -> {
            eventService.getEventByName("diesdas");
        });
    }

    @Test
    void shouldGetAllEventsByUser() {
        given(businessUserService.getBusinessUser("max@mustermann.de")).willReturn(user);
        given(eventRepository.findAllByUserId(user)).willReturn(events);
        given(mapperService.convertToEventTO(events)).willReturn(eventTOs);
        Set<EventTO> returnedSet = eventService.getAllEventsByUser("max@mustermann.de");
        assertEquals(eventTOs, returnedSet);
    }

    @Test
    void shouldNotGetAllEventsByUser() {
        HashSet<Event> emptySet = new HashSet<Event>();
        given(businessUserService.getBusinessUser("max@mustermann.de")).willReturn(user);
        given(eventRepository.findAllByUserId(user)).willReturn(emptySet);
        assertThrows(ResourceNotFoundException.class, () -> {
            eventService.getAllEventsByUser("max@mustermann.de");
        });
    }

    @Test
    void shouldGetAllEventsByPreference() {
        given(eventRepository.findallByPreference("value")).willReturn(events);
        given(mapperService.convertToEventTO(events)).willReturn(eventTOs);
        Set<EventTO> returnedSet = eventService.getAllEventsByPreference("value");
        assertEquals(eventTOs, returnedSet);
    }

    @Test
    void shouldNotGetAllEventsByPreference() {
        HashSet<Event> emptySet = new HashSet<Event>();
        given(eventRepository.findallByPreference("value")).willReturn(emptySet);
        assertThrows(ResourceNotFoundException.class, () -> {
            eventService.getAllEventsByPreference("value");
        });
    }




}
