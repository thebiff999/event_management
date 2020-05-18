package de.fhms.sweng.event_management.rest;

import de.fhms.sweng.event_management.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import de.fhms.sweng.event_management.entities.Event;

import java.util.Optional;


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
    @GetMapping("")
    public Event getEventById(@RequestParam(value="id")int id){
        return eventService.getEventById(id);
    }
/*
    @GetMapping("/byUser")
    public void getEventByUser() {

    }
*/
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvent(@RequestBody Event newEvent) {

    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@RequestParam(value="id")int id) {

    }


}