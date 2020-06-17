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

/**
 * Spring service that converts data transfer objects into entities and vice versa
 * @author Dennis Heuermann
 */
@Service
public class MapperService {


    private final Logger LOGGER = LoggerFactory.getLogger(getClass());


    /**
     * empty constructor
     */
    public MapperService() {
    }

    /**
     * converts a set of event entities into a set of event DTOs
     * @param eventSet set to be converted
     * @return converted set
     */
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

    /**
     * converts an event entity into an event DTO
     * @param event event to be converted
     * @return converted event entity
     */
    public EventTO convertToEventTO(Event event) {

        LOGGER.info("converting Event into EventTO");
        LOGGER.debug("Values of Event:");
        LOGGER.debug("{}", event.toString());
        EventTO eventTO = new EventTO(event);
        LOGGER.debug("Values of mapped EventTO:");
        LOGGER.debug("{}", eventTO.toString());
        return eventTO;
    }


    /**
     * converts a BusinessUser DTO into a BusinessUser entitiy
     * @param businessUserTO dto to be converted
     * @return converted BusinessUser entitiy
     */
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
