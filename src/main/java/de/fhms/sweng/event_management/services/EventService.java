package de.fhms.sweng.event_management.services;

import de.fhms.sweng.event_management.dto.EventTO;
import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.entities.Event;
import de.fhms.sweng.event_management.entities.Preference;
import de.fhms.sweng.event_management.exceptions.IdMismatchException;
import de.fhms.sweng.event_management.exceptions.ResourceNotFoundException;
import de.fhms.sweng.event_management.repositories.EventRepository;
import de.fhms.sweng.event_management.producer.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class EventService {

    private EventRepository eventRepository;
    private BusinessUserService businessUserService;
    private PreferenceService preferenceService;
    private MapperService mapperService;
    private EventProducer eventProducer;

    @Autowired
    public EventService(EventRepository eventRepository, BusinessUserService businessUserService, PreferenceService preferenceService, MapperService mapperService, EventProducer eventProducer) {
        this.eventRepository = eventRepository;
        this.businessUserService = businessUserService;
        this.preferenceService = preferenceService;
        this.mapperService = mapperService;
        this.eventProducer = eventProducer;
    }


    @Transactional
    public EventTO createEvent(EventTO eventTO) {
        Event event = new Event();
        event = mapperService.convertToEvent(eventTO);
        preferenceService.createPreferencesFromEvent(event);
        event = eventRepository.save(event);
        EventTO newEventTO = mapperService.convertToEventTO(event);
        eventProducer.sendNewEvent(newEventTO);
        return newEventTO;
    }


    public void deleteEvent(int id) {

        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            EventTO eventTO = mapperService.convertToEventTO(optionalEvent.get());
            eventRepository.deleteById(id);
            eventProducer.sendDeletedEvent(eventTO);
        }
        else {throw new ResourceNotFoundException("Event has not been found"); }

    }

    public EventTO updateEvent(int id, EventTO eventTO) {

            Event updatedEvent = mapperService.convertToEvent(eventTO);
            Optional<Event> optionalEvent = eventRepository.findById(id);
            if (optionalEvent.isPresent()) {
                Event oldEvent = optionalEvent.get();
                updatedEvent.setId(id);
                if (oldEvent.getPreferences() != updatedEvent.getPreferences()) {
                    preferenceService.createPreferencesFromEvent(updatedEvent);
                    eventProducer.sendNewEvent(eventTO);
                }

                return mapperService.convertToEventTO(eventRepository.save(updatedEvent));
            }
            else { throw new ResourceNotFoundException("Event not found"); }

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
