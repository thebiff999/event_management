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

    public Optional<Preference> getPrefernceByValue(String value) {
        return preferenceRepository.findByValue(value);
    }

    public void createPreferencesFromEvent(EventTO event) {
        LOGGER.info("creating preferences from event");
        if (event.hasPreferences()) {
            for (Preference p : event.getPreferences()) {
                if (getPrefernceByValue(p.getValue()).isPresent()) {
                    Preference preference = getPrefernceByValue(p.getValue()).get();
                    LOGGER.trace("Preference with value {} already exists with id {}", preference.getValue(), preference.getId());
                }
                else {
                    LOGGER.trace("Preference with value {} doesn't exit yet, creating new one", p.getValue());
                    Preference newPreference = new Preference();
                    newPreference.setValue(p.getValue());
                    preferenceRepository.save(newPreference);
                    LOGGER.trace("created preference '{}-{}'", newPreference.getId(), newPreference.getValue());
                }
            }
        }
    }

}
