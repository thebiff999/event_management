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
    private EventRepository eventRepository;
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
        LOGGER.debug("Values of Event:");
        LOGGER.debug("{}", event.toString());
        EventTO eventTO = new EventTO(event);
        LOGGER.debug("Values of mapped EventTO:");
        LOGGER.debug("{}", eventTO.toString());
        return eventTO;
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
