package de.fhms.sweng.event_management.services;

import de.fhms.sweng.event_management.dto.EventTO;
import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.entities.Event;
import de.fhms.sweng.event_management.exceptions.IdMismatchException;
import de.fhms.sweng.event_management.exceptions.ResourceNotFoundException;
import de.fhms.sweng.event_management.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class EventService {

    private EventRepository eventRepository;
    private BusinessUserService businessUserService;
    private MapperService mapperService;

    @Autowired
    public EventService(EventRepository eventRepository, BusinessUserService businessUserService, MapperService mapperService) {
        this.eventRepository = eventRepository;
        this.businessUserService = businessUserService;
        this.mapperService = mapperService;
    }


    //TODO: add rabbit message for lennart
    public EventTO createEvent(EventTO eventTO) {
        Event event = mapperService.convertToEvent(eventTO);
        return mapperService.convertToEventTO(eventRepository.save(event));
    }

    //TODO: add rabbit message for lennart
    public void deleteEvent(int id) {
        eventRepository.deleteById(id);
    }

    public EventTO updateEvent(int id, EventTO eventTO) {

        if (eventTO.getId() != id) {
            throw new IdMismatchException();
        }
        else {
            Event updatedEvent = mapperService.convertToEvent(eventTO);
            Optional<Event> optionalEvent = eventRepository.findById(id);
            if (optionalEvent.isPresent()) {
                Event oldEvent = optionalEvent.get();
                //TODO: add rabbit messages for lennart
                if (oldEvent.getPreferences() != updatedEvent.getPreferences()) {
                    System.out.println("Send messages over AMQP");
                }

                return mapperService.convertToEventTO(eventRepository.save(updatedEvent));
            }
            else { throw new ResourceNotFoundException("Event not found"); }
        }
    }

    public Set<EventTO> getEvents() {

        Iterable<Event> events = eventRepository.findAll();
        return mapperService.convertToEventTO(events);

    }

    public Event getEventById(int id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            return optionalEvent.get();
        }
        else throw new ResourceNotFoundException("Event not found");
    }

    public EventTO getEventTOById(int id) {

        EventTO eventTO;
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            return eventTO = new EventTO(optionalEvent.get());
        }
        else throw new ResourceNotFoundException("Event not found");
    }

    public Set<EventTO> getEventByName(String name) {

        Set<Event> events = eventRepository.findByName(name);
        if (!(events.isEmpty())) {
            return mapperService.convertToEventTO(events);
        }
        else throw new ResourceNotFoundException("Event not found");
    }


    public Set<EventTO> getAllEventsByUser(int id) {

        Set<Event> events = eventRepository.findAllByUserId(id);
        if (!(events.isEmpty())) {
            return mapperService.convertToEventTO(events);
        }
        else throw new ResourceNotFoundException("Event not found");
    }

    public Set<EventTO> getAllEventsByPreference(String preference) {
        Set<Event> events = eventRepository.findallByPreference(preference);
        if (!(events.isEmpty())) {
            return mapperService.convertToEventTO(events);
        }
        else throw new ResourceNotFoundException("Event not found");
    }


}
