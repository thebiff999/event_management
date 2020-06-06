package de.fhms.sweng.event_management.services;

import de.fhms.sweng.event_management.dto.EventTO;
import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.entities.Event;
import de.fhms.sweng.event_management.entities.Preference;
import de.fhms.sweng.event_management.exceptions.IdMismatchException;
import de.fhms.sweng.event_management.exceptions.ResourceNotFoundException;
import de.fhms.sweng.event_management.repositories.EventRepository;
import de.fhms.sweng.event_management.producer.EventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

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
        LOGGER.trace("creating new event");
        Event event = new Event();
        LOGGER.info("new event with id {} created", event.getId());
        event = mapperService.convertToEvent(eventTO);
        preferenceService.createPreferencesFromEvent(event);
        event = eventRepository.save(event);
        EventTO newEventTO = mapperService.convertToEventTO(event);
        eventProducer.sendNewEvent(newEventTO);
        return newEventTO;
    }


    public void deleteEvent(int id) {
        LOGGER.info("deleting event wit id {}", id);
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            EventTO eventTO = mapperService.convertToEventTO(optionalEvent.get());
            eventRepository.deleteById(id);
            eventProducer.sendDeletedEvent(eventTO);
        }
        else {
            LOGGER.error("event with id {} has not been found", id);
            throw new ResourceNotFoundException("Event has not been found");
        }

    }

    public EventTO updateEvent(int id, EventTO eventTO) {

            LOGGER.info("updating event with id {}", id);
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
            else {
                LOGGER.error("event with id {} has not been found", id);
                throw new ResourceNotFoundException("Event not found");
            }

    }

    public Set<EventTO> getEvents() {

        Iterable<Event> events = eventRepository.findAll();
        LOGGER.info("Returning all events");
        return mapperService.convertToEventTO(events);

    }

    public Event getEventById(int id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            LOGGER.info("Returning event with id {}", id);
            return optionalEvent.get();
        }
        else {
            LOGGER.error("event with id {} has not been found", id);
            throw new ResourceNotFoundException("Event not found");
        }
    }

    public EventTO getEventTOById(int id) {

        EventTO eventTO;
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            LOGGER.info("Returning eventTO with id {}", id);
            return eventTO = new EventTO(optionalEvent.get());
        }
        else {
            LOGGER.error("eventTO with id {} has not been found", id);
            throw new ResourceNotFoundException("Event not found");
        }
    }

    public Set<EventTO> getEventByName(String name) {

        Set<Event> events = eventRepository.findByName(name);
        if (!(events.isEmpty())) {
            LOGGER.info("returning event with name '{}'", name);
            return mapperService.convertToEventTO(events);
        }
        else {
            LOGGER.error("no event with name '{}' could be found", name);
            throw new ResourceNotFoundException("Event not found");
        }
    }


    public Set<EventTO> getAllEventsByUser(int id) {

        Set<Event> events = eventRepository.findAllByUserId(id);
        if (!(events.isEmpty())) {
            LOGGER.info("Returning all events by user with id {}", id);
            return mapperService.convertToEventTO(events);
        }
        else {
            LOGGER.error("could not find any events by user with id {}", id);
            throw new ResourceNotFoundException("Event not found");
        }
    }

    public Set<EventTO> getAllEventsByPreference(String preference) {
        Set<Event> events = eventRepository.findallByPreference(preference);
        if (!(events.isEmpty())) {
            LOGGER.info("Returning alls events with preference '{}'", preference);
            return mapperService.convertToEventTO(events);
        }
        else {
            LOGGER.error("could not find any events with preference '{}'", preference);
            throw new ResourceNotFoundException("Event not found");
        }
    }


}
