package de.fhms.sweng.event_management.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import de.fhms.sweng.event_management.entities.Event;


@RestController
@RequestMapping("/event")
public class EventRestController {

    @GetMapping("/test")
    public String getTest() {
        return "Test";
    }

    @GetMapping("/byName")
    public void getEventByName(@RequestParam(value="name")String name) {

    }
}