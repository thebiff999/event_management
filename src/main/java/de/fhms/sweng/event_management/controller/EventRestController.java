package de.fhms.sweng.event_management.controller;

import de.fhms.sweng.event_management.dto.EventTO;
import de.fhms.sweng.event_management.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import de.fhms.sweng.event_management.entities.Event;

import java.util.Set;


@RestController
@RequestMapping("/event")
public class EventRestController {

    private EventService eventService;

    @Autowired
    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }


    @GetMapping("/all")
    public Set<EventTO> getAllEvents() {
        return eventService.getEvents();
    }

    @GetMapping("/byName")
    public Set<EventTO> getEventByName(@RequestParam(value="name")String name) {
        return eventService.getEventByName(name);
    }

    @GetMapping("/byId")
    public EventTO getEventById(@RequestParam(value="id")int id){
        return eventService.getEventTOById(id);
    }

    @GetMapping("/test")
    public String getTest() {
        return "test successful";
    }

    @GetMapping("/byUser")
    public Set<EventTO> getEventByUser(@RequestParam(value="id")int id) {
        return eventService.getAllEventsByUser(id);
    }

    @GetMapping("/byPreference")
    public Set<EventTO> getEventByPreference(@RequestParam(value="preference")String preference) {
        return eventService.getAllEventsByPreference(preference);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public EventTO createEvent(@RequestBody EventTO newEvent) {
        return eventService.createEvent(newEvent);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable(value="id")int id) {
        eventService.deleteEvent(id);
    }

    @PutMapping("/{id}")
    public EventTO updateEvent(@PathVariable("id") int id, @RequestBody EventTO updatedEvent) {
        return eventService.updateEvent(id,updatedEvent);
    }


}