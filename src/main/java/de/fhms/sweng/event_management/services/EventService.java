package de.fhms.sweng.event_management.services;

import de.fhms.sweng.event_management.dto.EventTO;
import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.entities.Event;
import de.fhms.sweng.event_management.entities.Preference;
import de.fhms.sweng.event_management.exceptions.ResourceNotFoundException;
import de.fhms.sweng.event_management.repositories.EventRepository;
import de.fhms.sweng.event_management.producer.EventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

/**
 * Spring service that handles tasks related to Event entities
 * @author Dennis Heuermann
 */
@Service
public class EventService {

    private EventRepository eventRepository;
    private BusinessUserService businessUserService;
    private PreferenceService preferenceService;
    private MapperService mapperService;
    private EventProducer eventProducer;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * constructor with dependency injection for EventRepository, BusinessUserRepository, PreferenceService, MapperService and EventProducer
     * @param eventRepository
     * @param businessUserService
     * @param preferenceService
     * @param mapperService
     * @param eventProducer
     */
    @Autowired
    public EventService(EventRepository eventRepository, BusinessUserService businessUserService, PreferenceService preferenceService, MapperService mapperService, EventProducer eventProducer) {
        this.eventRepository = eventRepository;
        this.businessUserService = businessUserService;
        this.preferenceService = preferenceService;
        this.mapperService = mapperService;
        this.eventProducer = eventProducer;
    }


    /**
     * creates Event entity from a Data Transfer Object and adds it to the event repository
     * maps the event to corresponding BusinessUSer and Preference entities
     * calls the EventProducer to send the created event as a DTO to a message server
     * @param eventTO mapped DTO from event parameter
     * @return
     */
    @Transactional
    public EventTO createEvent(EventTO eventTO) {
        LOGGER.info("start of createEvent");
        LOGGER.trace(" create new event entity");
        Event event = new Event();
        LOGGER.trace(" mapping simple data types");
        //map simple data types
        event.setName(eventTO.getName());
        if (eventTO.hasDescription()) { event.setDescription(eventTO.getDescription());}
        event.setDatetime(eventTO.getDatetime());
        event.setRadius(eventTO.getRadius());
        event.setLongitude(eventTO.getLongitude());
        event.setLatitude(eventTO.getLatitude());
        //map business user
        LOGGER.trace(" mapping Business User {}", eventTO.getBusinessUserId());
        BusinessUser businessUser = businessUserService.getBusinessUser(eventTO.getBusinessUserId());
        event.setBusinessUserId(businessUser);
        //map preferences
        LOGGER.trace(" mapping preferences");
        LOGGER.trace("  create prefrences from eventTO");
        preferenceService.createPreferencesFromEvent(eventTO);
        for (Preference p : eventTO.getPreferences()) {
            Preference preference = preferenceService.getPrefernceByValue(p.getValue()).get();
            LOGGER.trace("  mapping preference {}-{}", preference.getId(), preference.getValue());
            event.addPreference(preference);
        }
        event = eventRepository.save(event);
        LOGGER.info("new event with id {} successfully created", event.getId());
        EventTO newEventTO = mapperService.convertToEventTO(event);
        eventProducer.sendNewEvent(newEventTO);
        return newEventTO;
    }

    /**
     * deletes an event entitiy from the event repository
     * throws a ResourceNotFoundException if there is no event with the requested id
     * calls the EventProducer to send the deleted event as a DTO to a message server
     * @param id
     */
    @Transactional
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

    /**
     * updates an existing event in the event repository
     * checks if there actually are changes to the event
     * calls the PreferenceService is the updated event contains new preferences
     * @param id id of event that should be updated
     * @param eventTO updated event dto
     * @return
     */
    @Transactional
    public EventTO updateEvent(int id, EventTO eventTO) {

            LOGGER.info("starting update of event with id {}", id);
            LOGGER.trace("fetching event by id");
            Event event = getEventById(id);
            //Check if there are any updates to the event
            if (mapperService.convertToEventTO(event).equals(eventTO)) {
                LOGGER.info("there are no changes to the event");
                return eventTO;
            }
            LOGGER.trace(" mapping simple data types");
            event.setName(eventTO.getName());
            if (eventTO.hasDescription()) {
                event.setDescription(eventTO.getDescription());
            }
            else {
                event.setDescription(null);
            }
            event.setDatetime(eventTO.getDatetime());
            if (!(event.getLongitude() == eventTO.getLongitude() && event.getLatitude() == eventTO.getLatitude())) {
                event.setLongitude(eventTO.getLongitude());
                event.setLatitude(eventTO.getLatitude());
            }
            //map preferences
            LOGGER.trace(" mapping preferences");
            if (!(mapperService.convertToEventTO(event).getPreferences() == eventTO.getPreferences())) {
                LOGGER.trace("  create prefrences from eventTO");
                preferenceService.createPreferencesFromEvent(eventTO);
                for (Preference p : eventTO.getPreferences()) {
                    Preference preference = preferenceService.getPrefernceByValue(p.getValue()).get();
                    LOGGER.trace("  mapping preference {}-{}", preference.getId(), preference.getValue());
                    event.addPreference(preference);
                }

            }
            event = eventRepository.save(event);
            LOGGER.info("event with id {} successfully updated", event.getId());
            EventTO updatedEventTO = mapperService.convertToEventTO(event);
            eventProducer.sendNewEvent(updatedEventTO);
            return updatedEventTO;


    }

    /**
     * returns a set with every event in the repository
     * @return set of every event
     */
    public Set<EventTO> getEvents() {

        Iterable<Event> events = eventRepository.findAll();
        LOGGER.info("Returning all events");
        return mapperService.convertToEventTO(events);

    }

    /**
     * returns an event with the requested id
     * throws a ResourceNotFoundException if there is no event with the requested id
     * @param id search parameter
     * @return event with requested id
     */
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

    /**
     * returns a data transfer object of an event with the requested id
     * throws a ResourceNotFoundException if there is no event with the requested id
     * @param id search parameter
     * @return event dto with requested id
     */
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

    /**
     * returns a set of every event that contains the requested parameter
     * throws a ResourceNotFoundException if no event matches the parameter
     * @param name search parameter
     * @return set of events that match the request parameter
     */
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


    /**
     * returns a set of event DTOs with every event from the requested user mail
     * throws a ResourceNotFoundException if there are no events by the requested user
     * @param mail search parameter
     * @return set of events from requested user
     */
    public Set<EventTO> getAllEventsByUser(String mail) {

        LOGGER.trace("trying to get all events from user {}", mail);
        BusinessUser user = businessUserService.getBusinessUser(mail);
        int id = user.getId();

        Set<Event> events = eventRepository.findAllByUserId(user);
        if (!(events.isEmpty())) {
            LOGGER.info("Returning all events by user with id {}", id);
            return mapperService.convertToEventTO(events);
        }
        else {
            LOGGER.error("could not find any events by user with id {}", id);
            throw new ResourceNotFoundException("Event not found");
        }
    }

    /**
     * returns a set of event DTOs that match the requested preference
     * throws a ResourceNotFoundException if no event matches the requested preference
     * @param preference search parameter
     * @return set of events matching the request parameter
     */
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
