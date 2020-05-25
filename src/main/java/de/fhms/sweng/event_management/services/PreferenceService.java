package de.fhms.sweng.event_management.services;

import de.fhms.sweng.event_management.entities.Preference;
import de.fhms.sweng.event_management.repositories.PreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PreferenceService {

    private PreferenceRepository preferenceRepository;

    @Autowired
    public PreferenceService(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    public Optional<Preference> getPrefernceByName(String name) {
        return preferenceRepository.findByName(name);
    }

}
