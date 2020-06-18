package de.fhms.sweng.event_management;

import de.fhms.sweng.event_management.consumer.UserConsumer;
import de.fhms.sweng.event_management.dto.EventTO;
import de.fhms.sweng.event_management.entities.Event;
import de.fhms.sweng.event_management.entities.Preference;
import de.fhms.sweng.event_management.exceptions.ResourceNotFoundException;
import de.fhms.sweng.event_management.repositories.EventRepository;
import de.fhms.sweng.event_management.services.EventService;
import de.fhms.sweng.event_management.services.MapperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
@EnableFeignClients
class EventManagementApplicationTest {


    private EventTO eventTO;
    private LocalDateTime time;
    private Preference pref;
    private Set<Preference> prefSet;
    private int toDelete;

    @Autowired
    private EventService eventService;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    MapperService mapperService;

    @Test
    public void contextLoads() {
    }

    @BeforeEach
    public void setUp() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String datetime = "2020-06-06 20:00";
        time = LocalDateTime.parse(datetime, formatter);

        pref = new Preference();
        pref.setId(3);
        pref.setValue("New");

        prefSet = new HashSet<Preference>();
        prefSet.add(pref);

        eventTO = new EventTO(3,1,"NewEvent","NewDescription", time, 3, 30.00, 30.00, prefSet);
    }


    /*@Test
    void shouldCreateEvent() {
        EventTO newEvent = eventService.createEvent(eventTO);
        int id = newEvent.getId();
        toDelete = id;
        Optional<Event> optEvent = eventRepository.findById(id);
        if (!(optEvent.isEmpty())) {
            Event event = optEvent.get();
            EventTO returnedEvent = mapperService.convertToEventTO(event);
            assertEquals(newEvent, returnedEvent);
        }

    }


    @Test
    void shouldDeleteEvent() {
        eventService.deleteEvent(1);
        assertThrows(ResourceNotFoundException.class, () -> {
            eventRepository.findById(1);
        });
    }*/

    @Test
    void shouldNotDeleteEvent() {
        assertThrows(ResourceNotFoundException.class, () -> {
            eventService.deleteEvent(6);
        });
    }

    @Test
    void shouldGetAllEventsByUser() {
        Set<EventTO> eventSet = eventService.getAllEventsByUser("mail@test.com");
        Iterator<EventTO> eventIterator = eventSet.iterator();
        EventTO expected = eventService.getEventTOById(1);
        assertEquals(expected.getId(), eventIterator.next().getId());
    }

    @Test
    void shouldGetAllEvents() {
        Set<EventTO> eventSet = eventService.getEvents();
        assertFalse(eventSet.isEmpty());
    }

    @Test
    void shouldGetEventById() {
        Event event = eventService.getEventById(1);
        assertEquals(1, event.getId());
    }

    @Test
    void shouldNotGetEventById() {
        assertThrows(ResourceNotFoundException.class, () -> {
            eventService.getEventById(10);
        });
    }

    @Test
    void shouldGetEventTOById() {
        EventTO event = eventService.getEventTOById(1);
        assertEquals(1, event.getId());
    }

    @Test
    void shouldNotGetEventTOById() {
        assertThrows(ResourceNotFoundException.class, () -> {
            eventService.getEventTOById(10);
        });
    }




}
