package de.fhms.sweng.event_management.controller;

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
/*
    @GetMapping("/byName")
    public void getEventByName(@RequestParam(value="name")String name) {

    }
*/

    @GetMapping("byName")
    public Set<Event> getEventByName(@RequestParam(value="name")String name) {
        return eventService.getEventByName(name);
    }

    @GetMapping("/byId")
    public Event getEventById(@RequestParam(value="id")int id){
        return eventService.getEventById(id);
    }

    @GetMapping("/test")
    public String getTest() {
        return "test successful";
    }

    @GetMapping("/byUser")
    public void getEventByUser(@RequestParam(value="id")int id) {
        return eventService.getAllEventsByUser(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvent(@RequestBody Event newEvent) {

    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@RequestParam(value="id")int id) {

    }


}