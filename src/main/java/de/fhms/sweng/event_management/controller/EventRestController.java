package de.fhms.sweng.event_management.controller;

import de.fhms.sweng.event_management.dto.EventTO;
import de.fhms.sweng.event_management.services.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/event")
public class EventRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private EventService eventService;

    @Autowired
    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }


    @GetMapping("/all")
    public Set<EventTO> getAllEvents() {
        LOGGER.info("GET-Request on /all recieved");
        return eventService.getEvents();
    }

    @GetMapping("/byName")
    public Set<EventTO> getEventByName(@RequestParam(value="name")String name) {
        LOGGER.info("GET-Request on /byName revieced with parameter {}", name);
        return eventService.getEventByName(name);
    }

    @GetMapping("/byId")
    public EventTO getEventById(@RequestParam(value="id")int id){
        LOGGER.info("GET-Request on /byID recieved with parameter {}", id);
        return eventService.getEventTOById(id);
    }

    @GetMapping("/test")
    public String getTest() {
        LOGGER.info("GET-Resquest on /test recieved");
        return "Event Microservice is available";
    }

    @GetMapping("/byUser")
    public Set<EventTO> getEventByUser(@RequestParam(value="id")int id) {
        LOGGER.info("GET-Request on /byUser recieved with parameter {}", id);
        return eventService.getAllEventsByUser(id);
    }

    @GetMapping("/byPreference")
    public Set<EventTO> getEventByPreference(@RequestParam(value="preference")String preference) {
        LOGGER.info("GET-Request on /byPreference recieved with parameter {}", preference);
        return eventService.getAllEventsByPreference(preference);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public EventTO createEvent(@RequestBody EventTO newEvent) {
        LOGGER.info("POST-Request recieved");
        return eventService.createEvent(newEvent);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable(value="id")int id) {
        LOGGER.info("DELETE-Mapping recieved with id {}", id);
        eventService.deleteEvent(id);
    }

    @PutMapping("/{id}")
    public EventTO updateEvent(@PathVariable("id") int id, @RequestBody EventTO updatedEvent) {
        LOGGER.info("PUT-Mapping recieved with id {}", id);
        return eventService.updateEvent(id,updatedEvent);
    }


}