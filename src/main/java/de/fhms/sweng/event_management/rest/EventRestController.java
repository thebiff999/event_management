package de.fhms.sweng.event_management.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import de.fhms.sweng.event_management.entities.Event;


@RestController
@RequestMapping("/event")
public class EventRestController {

/*
    @GetMapping("/byName")
    public void getEventByName(@RequestParam(value="name")String name) {

    }

    @GetMapping("/byId")
    public void getEventById(@RequestParam(value="id")int id){

    }

    @GetMapping("/byUser")
    public void getEventByUser() {

    }
*/
    @PostMapping("/newEvent")
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvent(@RequestBody Event newEvent) {

    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@RequestParam(value="id")int id) {

    }


}