package de.fhms.sweng.event_management.services;

import de.fhms.sweng.event_management.dto.EventTO;
import de.fhms.sweng.event_management.entities.Event;
import de.fhms.sweng.event_management.exceptions.ResourceNotFoundException;
import de.fhms.sweng.event_management.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

@Service
public class EventService {

    private EventRepository eventRepository;
    private BusinessUserService businessUserService;

    @Autowired
    public EventService(EventRepository eventRepository, BusinessUserService businessUserService) {
        this.eventRepository = eventRepository;
        this.businessUserService = businessUserService;
    }

    public Iterable<EventTO> getEvents() {

        Iterable<Event> events = eventRepository.findAll();
        Set<EventTO> eventTOs = new Set<EventTO>();

        for (Event e:events) {
            EventTO eventTO = new EventTO(e);
            eventTOs.add(eventTO);
        }
        return eventTOs;

    }

    public Event getEventById(int id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            return optionalEvent.get();
        }
        else throw new ResourceNotFoundException("Event not found");
    }

    public Set<Event> getEventByName(String name) {
        Set<Event> eventSet = eventRepository.findByName(name);
        if (!(eventSet.isEmpty())) {
            return eventSet;
        }
        else throw new ResourceNotFoundException("Event not found");
    }


    public Set<Event> getAllEventsByUser(int id) {
        Set<Event> eventSet = eventRepository.findAllByUserId(id);
        if (!(eventSet.isEmpty())) {
            return eventSet;
        }
        else throw new ResourceNotFoundException("Event not found");
    }




}
