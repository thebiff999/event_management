package de.fhms.sweng.event_management.services;

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

    @Autowired
    public EventService(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    public Iterable<Event> getEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(int id) {
        if (eventRepository.existsById(id)) {
            return eventRepository.findById(id);
        }
        else throw new ResourceNotFoundException("Event not found");
    }



}
