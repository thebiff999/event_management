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

/**
 * REST API Controller for interacting with the services
 * @author Dennis Heuermann
 */
@RestController
@RequestMapping("/event")
public class EventRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private EventService eventService;
    private BusinessUserService userService;
    private JwtTokenProvider jwtTokenProvider;

    /**
     * standard constructor
     * @param eventService Event Service
     * @param userService BusinessUSer Service
     * @param jwtTokenProvider JwtTokenProvider
     */
    @Autowired
    public EventRestController(EventService eventService, BusinessUserService userService, JwtTokenProvider jwtTokenProvider) {
        this.eventService = eventService;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * returns all events in the database
     * @return set of all events
     */
    @GetMapping("/all")
    public Set<EventTO> getAllEvents() {
        LOGGER.info("GET-Request on /all received");
        return eventService.getEvents();
    }


    /**
     * returns a set of events which include the name given as parameter
     * @param name the name which the event service will look up and return events by
     * @return event set
     */
    @GetMapping("/byName")
    public Set<EventTO> getEventByName(@RequestParam(value="name")String name) {
        LOGGER.info("GET-Request on /byName received with parameter {}", name);
        return eventService.getEventByName(name);
    }

    /**
     * returns the event with matching id
     * @param id request id
     * @return single event
     */
    @GetMapping("/byId")
    public EventTO getEventById(@RequestParam(value="id")int id){
        LOGGER.info("GET-Request on /byID received with parameter {}", id);
        return eventService.getEventTOById(id);
    }

    /**
     * healthcheck to see if the servie is available
     * @return simple string
     */
    @GetMapping("/healthcheck")
    @ResponseStatus(HttpStatus.OK)
    public String getTest() {
        LOGGER.trace("GET-Resquest on /healthcheck recieved");
        return "Event Microservice is available";
    }

    /**
     * returns the events from requesting user
     * @param Authorization requesting user is read from the Auth Header
     * @return event set
     */
    @GetMapping("/byUser")
    @PreAuthorize("hasAuthority('EUSER')")
    public Set<EventTO> getEventByUser(@CookieValue(name="Authorization") String Authorization) {
        String mail = jwtTokenProvider.getUsername(Authorization);
        LOGGER.info("GET-Request on /byUser recieved from user {}", mail);
        return eventService.getAllEventsByUser(mail);
    }

    /**
     * returns all events that include the requested preference
     * @param preference search parameter
     * @return event set
     */
    @GetMapping("/byPreference")
    public Set<EventTO> getEventByPreference(@RequestParam(value="preference")String preference) {
        LOGGER.info("GET-Request on /byPreference recieved with parameter {}", preference);
        return eventService.getAllEventsByPreference(preference);
    }

    /**
     * POST-API for adding new events
     * @param newEvent event to be added to database
     * @param Authorization Authorization Header
     * @return posted event as confirmation
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority('EUSER')")
    @ResponseStatus(HttpStatus.CREATED)
    public EventTO createEvent(@RequestBody EventTO newEvent, @CookieValue(name="Authorization") String Authorization) {
        LOGGER.info("POST-Request recieved");
        String mail = jwtTokenProvider.getUsername(Authorization);
        newEvent.setBusinessUserId(userService.getBusinessUser(mail).getId());
        return eventService.createEvent(newEvent);
    }

    /**
     * DELETE-API for deleting events from database
     * @param id id of event that should be deleted
     * @param Authorization Authorization Header
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EUSER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable(value="id")int id, @CookieValue(name="Authorization") String Authorization) {
        LOGGER.info("DELETE-Mapping recieved with id {}", id);
        LOGGER.debug("Checking if the event with id {} belongs to the requesting user {}", id, Authorization.substring(7));
        String mail = jwtTokenProvider.getUsername(Authorization);
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

    /**
     * PUT-API to update existing events
     * @param id id of event that should be updated
     * @param updatedEvent updated version of event
     * @param Authorization Authorization Header
     * @return updated event as confirmation
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EUSER')")
    @ResponseStatus(HttpStatus.OK)
    public EventTO updateEvent(@PathVariable("id") int id, @RequestBody EventTO updatedEvent, @CookieValue(name="Authorization") String Authorization) {
        LOGGER.info("PUT-Mapping recieved with id {}", id);
        LOGGER.debug("Checking if the event with id {} belongs to the requesting user {}", id, Authorization.substring(7));
        String mail = jwtTokenProvider.getUsername(Authorization);
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