package de.fhms.sweng.event_management.services;

import de.fhms.sweng.event_management.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    public EventService(EventRepository eventRepository){

    }

}
