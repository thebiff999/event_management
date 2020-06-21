package de.fhms.sweng.event_management.services;

import de.fhms.sweng.event_management.dto.EventTO;
import de.fhms.sweng.event_management.entities.Event;
import de.fhms.sweng.event_management.entities.Preference;
import de.fhms.sweng.event_management.repositories.PreferenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static javax.transaction.Transactional.TxType.SUPPORTS;

/**
 * Spring service that handles tasks related to preference entities
 * @author Dennis Heuermann
 */
@Service
public class PreferenceService {

    private PreferenceRepository preferenceRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * constructor with dependency injection for PreferenceRepository
     * @param preferenceRepository Preference Repository
     */
    @Autowired
    public PreferenceService(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    /**
     * returns an Optional of Preference that matches the requested value
     * @param value search parameter
     * @return Optional of Preference with requested value
     */
    public Optional<Preference> getPrefernceByValue(String value) {
        return preferenceRepository.findByValue(value);
    }

    /**
     * iterates through the preference set of the event parameter and creates preferences that are not yet in the repository
     * @param event event whose preferences should be added
     */

    @Transactional(SUPPORTS)
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
