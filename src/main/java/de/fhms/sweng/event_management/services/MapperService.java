package de.fhms.sweng.event_management.services;

import de.fhms.sweng.event_management.dto.EventTO;
import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.entities.Event;
import de.fhms.sweng.event_management.entities.Location;
import de.fhms.sweng.event_management.entities.Preference;
import de.fhms.sweng.event_management.repositories.BusinessUserRepository;
import de.fhms.sweng.event_management.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MapperService {

    private BusinessUserService businessUserService;
    private PreferenceService preferenceService;

    @Autowired
    public MapperService(BusinessUserService businessUserService, PreferenceService preferenceService) {
        this.businessUserService = businessUserService;
        this.preferenceService = preferenceService;
    }

    public Set<EventTO> convertToEventTO(Iterable<Event> eventSet) {

        Set<EventTO> eventTOSet = new HashSet<EventTO>();

        for (Event e:eventSet) {
            EventTO eventTO = new EventTO(e);
            eventTOSet.add(eventTO);
        }

        return eventTOSet;

    }

    //Convert Event Entitiy to Data Transfer Object
    public EventTO convertToEventTO(Event event) {
        EventTO eventTO = new EventTO(event);
        return eventTO;
    }

    //Convert Data Transfer Object back to Event Entity
    public Event convertToEvent(EventTO eventTO) {

        //maybe add try catch block later
        Event event = new Event();
        //map the business user
        BusinessUser businessUser = businessUserService.getBusinessUser(eventTO.getBusinessUserId());

        //map the preferences
        if (eventTO.hasPreferences()) {
            event.setPreferences(eventTO.getPreferences());
            /*Set<Preference> preferences = new HashSet<Preference>();
            for (Preference p:preferences) {
                Preference preference = p;
                preference.addEvent(event);
                PreferenceService
            }*/
        }

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

}
