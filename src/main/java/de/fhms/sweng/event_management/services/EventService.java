package de.fhms.sweng.event_management.services;

import de.fhms.sweng.event_management.dto.EventTO;
import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.entities.Event;
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

    @Autowired
    public EventService(EventRepository eventRepository, BusinessUserService businessUserService) {
        this.eventRepository = eventRepository;
        this.businessUserService = businessUserService;
    }

    private Set<EventTO> convertToEventTO(Iterable<Event> eventSet) {

        Set<EventTO> eventTOSet = new HashSet<EventTO>();

        for (Event e:eventSet) {
            EventTO eventTO = new EventTO(e);
            eventTOSet.add(eventTO);
        }

        return eventTOSet;

    }

    private EventTO convertToEventTO(Event event) {
        EventTO eventTO = new EventTO(event);
        return eventTO;
    }


    public Event createEvent(EventTO eventTO) {
        BusinessUser businessUser = businessUserService.getBusinessUser(eventTO.getBusinessUserId());
        Event event = new Event(businessUser, eventTO);
        return eventRepository.save(event);
    }

    public Set<EventTO> getEvents() {

        Iterable<Event> events = eventRepository.findAll();
        return convertToEventTO(events);

    }

    public EventTO getEventById(int id) {

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
            return convertToEventTO(events);
        }
        else throw new ResourceNotFoundException("Event not found");
    }


    public Set<EventTO> getAllEventsByUser(int id) {

        Set<Event> events = eventRepository.findAllByUserId(id);
        if (!(events.isEmpty())) {
            return convertToEventTO(events);
        }
        else throw new ResourceNotFoundException("Event not found");
    }

    public Set<EventTO> getAllEventsByPreference(String preference) {
        Set<Event> events = eventRepository.findallByPreference(preference);
        if (!(events.isEmpty())) {
            return convertToEventTO(events);
        }
        else throw new ResourceNotFoundException("Event not found");
    }


}
