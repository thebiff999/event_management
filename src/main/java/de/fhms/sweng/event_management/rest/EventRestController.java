package de.fhms.sweng.event_management.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/event")
public class EventRestController {

    @GetMapping("/test")
    public String getTest() {
        return "Test";
    }
}