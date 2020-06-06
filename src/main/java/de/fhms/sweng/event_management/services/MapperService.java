package de.fhms.sweng.event_management.services;

import de.fhms.sweng.event_management.dto.BusinessUserTO;
import de.fhms.sweng.event_management.dto.EventTO;
import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.entities.Event;
import de.fhms.sweng.event_management.entities.Location;
import de.fhms.sweng.event_management.entities.Preference;
import de.fhms.sweng.event_management.repositories.BusinessUserRepository;
import de.fhms.sweng.event_management.repositories.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MapperService {

    private BusinessUserService businessUserService;
    private PreferenceService preferenceService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public MapperService(BusinessUserService businessUserService, PreferenceService preferenceService) {
        this.businessUserService = businessUserService;
        this.preferenceService = preferenceService;
    }

    public Set<EventTO> convertToEventTO(Iterable<Event> eventSet) {

        Set<EventTO> eventTOSet = new HashSet<EventTO>();
        LOGGER.info("starting to convert Event-Set into EventTO-Set");
        for (Event e:eventSet) {
            LOGGER.debug("starting to convert event with id {}", e.getId());
            EventTO eventTO = new EventTO(e);
            eventTOSet.add(eventTO);
        }
        LOGGER.info("completed converting Event-Set into EventTO-Set");
        return eventTOSet;

    }

    //Convert Event Entitiy to Data Transfer Object
    public EventTO convertToEventTO(Event event) {
        LOGGER.info("converting Event into EventTO");
        LOGGER.debug("id of event is {}", event.getId());
        EventTO eventTO = new EventTO(event);
        return eventTO;
    }

    //Convert Data Transfer Object back to Event Entity
    public Event convertToEvent(EventTO eventTO) {
        LOGGER.info("convert eventTO with Id {} to event", eventTO.getId());
        LOGGER.trace("creating Event and BusinessUser Entities");
        //maybe add try catch block later
        Event event = new Event();
        //map the business user
        BusinessUser businessUser = businessUserService.getBusinessUser(eventTO.getBusinessUserId());

        //map the preferences
        LOGGER.trace("mapping preferences");
        if (eventTO.hasPreferences()) {
            event.setPreferences(eventTO.getPreferences());
            /*Set<Preference> preferences = new HashSet<Preference>();
            for (Preference p:preferences) {
                Preference preference = p;
                preference.addEvent(event);
                PreferenceService
            }*/
        }
        LOGGER.trace("mapping other properties");
        event.setBusinessUserId(businessUser);
        event.setName(eventTO.getName());
        if (eventTO.getDescription() != null) {
            event.setDescription(eventTO.getDescription());
        }
        event.setDatetime(eventTO.getDatetime());
        event.setRadius(eventTO.getRadius());
        Location location = new Location(event,eventTO.getLongitude(),eventTO.getLatitude());
        event.setLocation(location);
        event.setPreferences(eventTO.getPreferences());
        return event;
    }

    public BusinessUser convertToUser(BusinessUserTO businessUserTO) {

        LOGGER.info("convert BusinessUserTO to BusinessUser");
        BusinessUser businessUser = new BusinessUser();
        businessUser.setId(businessUserTO.getId());
        businessUser.setFirstName(businessUserTO.getFirstName());
        businessUser.setLastName(businessUserTO.getLastName());
        businessUser.setMail(businessUserTO.getEmail());
        return businessUser;

    }

}
