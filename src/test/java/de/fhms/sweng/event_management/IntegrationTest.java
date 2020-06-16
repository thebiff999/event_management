package de.fhms.sweng.event_management;


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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext
@SpringBootTest
public class IntegrationTest {

    private EventTO eventTO;
    private LocalDateTime time;
    private Preference pref;
    private Set<Preference> prefSet;

    @Autowired
    private EventService eventService;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    MapperService mapperService;

    @BeforeEach
    public void setUp() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String datetime = "2020-06-06 20:00";
        time = LocalDateTime.parse(datetime, formatter);

        pref = new Preference();
        pref.setId(3);
        pref.setValue("New");

        eventTO = new EventTO(3,1,"NewEvent","NewDescription", time, 3, 30.00, 30.00, prefSet);
    }

    @Test
    void shouldCreateEvent() {
        EventTO newEvent = eventService.createEvent(eventTO);
        int id = newEvent.getId();
        Optional<Event> optEvent = eventRepository.findById(id);
        if (!(optEvent.isEmpty())) {
            Event event = optEvent.get();
            EventTO returnedEvent = mapperService.convertToEventTO(event);
            assertEquals(newEvent, returnedEvent);
        }

    }

    @Test
    void shouldDeleteEvent() {
        eventService.deleteEvent(0);
        Optional<Event> optEvent = eventRepository.findById(0);
        assertNull(optEvent.get());
    }

    @Test
    void shouldNotDeleteEvent() {
        assertThrows(ResourceNotFoundException.class, () -> {
            eventService.deleteEvent(6);
        });
    }

}
