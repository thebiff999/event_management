package de.fhms.sweng.event_management.controller;

import de.fhms.sweng.event_management.dto.EventTO;
import de.fhms.sweng.event_management.exceptions.NotAllowedException;
import de.fhms.sweng.event_management.security.JwtTokenProvider;
import de.fhms.sweng.event_management.services.BusinessUserService;
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
    private BusinessUserService userService;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public EventRestController(EventService eventService, BusinessUserService userService, JwtTokenProvider jwtTokenProvider) {
        this.eventService = eventService;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @GetMapping("/all")
    public Set<EventTO> getAllEvents() {
        LOGGER.info("GET-Request on /all received");
        return eventService.getEvents();
    }

    @GetMapping("/byName")
    public Set<EventTO> getEventByName(@RequestParam(value="name")String name) {
        LOGGER.info("GET-Request on /byName received with parameter {}", name);
        return eventService.getEventByName(name);
    }

    @GetMapping("/byId")
    public EventTO getEventById(@RequestParam(value="id")int id){
        LOGGER.info("GET-Request on /byID received with parameter {}", id);
        return eventService.getEventTOById(id);
    }

    @GetMapping("/healthcheck")
    @ResponseStatus(HttpStatus.OK)
    public String getTest() {
        LOGGER.info("GET-Resquest on /healthcheck recieved");
        return "Event Microservice is available";
    }

    @GetMapping("/byUser")
    @PreAuthorize("hasAuthority('EUSER')")
    public Set<EventTO> getEventByUser(@RequestHeader String Authorization) {
        String mail = jwtTokenProvider.getUsername(Authorization.substring(7));
        LOGGER.info("GET-Request on /byUser recieved from user {}", mail);
        return eventService.getAllEventsByUser(mail);
    }

    @GetMapping("/byPreference")
    public Set<EventTO> getEventByPreference(@RequestParam(value="preference")String preference) {
        LOGGER.info("GET-Request on /byPreference recieved with parameter {}", preference);
        return eventService.getAllEventsByPreference(preference);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('EUSER')")
    @ResponseStatus(HttpStatus.CREATED)
    public EventTO createEvent(@RequestBody EventTO newEvent, @RequestHeader String Authorization) {
        LOGGER.info("POST-Request recieved");
        String mail = jwtTokenProvider.getUsername(Authorization.substring(7));
        newEvent.setBusinessUserId(userService.getBusinessUser(mail).getId());
        return eventService.createEvent(newEvent);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EUSER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable(value="id")int id, @RequestHeader String Authorization) {
        LOGGER.info("DELETE-Mapping recieved with id {}", id);
        LOGGER.debug("Checking if the event with id {} belongs to the requesting user {}", id, Authorization.substring(7));
        String mail = jwtTokenProvider.getUsername(Authorization.substring(7));
        int userId = userService.getBusinessUser(mail).getId();
        int ownerId = eventService.getEventById(id).getBusinessUserId();
        if (userId == ownerId) {
            eventService.deleteEvent(id);
        }
        else {
            LOGGER.error("user with id {} tried to delete event with id {} which belongs to user with id {}", userId, id, ownerId);
            throw new NotAllowedException("not allowed to delete events from another user");
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EUSER')")
    @ResponseStatus(HttpStatus.OK)
    public EventTO updateEvent(@PathVariable("id") int id, @RequestBody EventTO updatedEvent, @RequestHeader String Authorization) {
        LOGGER.info("PUT-Mapping recieved with id {}", id);
        LOGGER.debug("Checking if the event with id {} belongs to the requesting user {}", id, Authorization.substring(7));
        String mail = jwtTokenProvider.getUsername(Authorization.substring(7));
        int userId = userService.getBusinessUser(mail).getId();
        int ownerId = eventService.getEventById(id).getBusinessUserId();
        if (userId == ownerId) {
            return eventService.updateEvent(id, updatedEvent);
        }
        else {
            throw new NotAllowedException("not allowed to update events from another user");
        }
    }


}