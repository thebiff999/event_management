package de.fhms.sweng.event_management.services;

import de.fhms.sweng.event_management.dto.EventTO;
import de.fhms.sweng.event_management.entities.Event;
import de.fhms.sweng.event_management.entities.Preference;
import de.fhms.sweng.event_management.repositories.PreferenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PreferenceService {

    private PreferenceRepository preferenceRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public PreferenceService(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public Optional<Preference> getPrefernceByName(String name) {
        LOGGER.info("Returning preference by name {}", name);
        return preferenceRepository.findByName(name);
    }

    public void createPreferencesFromEvent(Event event) {
        LOGGER.info("creating preferences from event with id {}", event.getId());
        if (event.hasPreferences()) {
            for (Preference p : event.getPreferences()) {
                p.addEvent(event);
                LOGGER.trace("adding preference '{}'", p.getValue());
                preferenceRepository.save(p);
            }
        }
    }

}
