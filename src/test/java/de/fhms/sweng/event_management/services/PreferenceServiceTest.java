package de.fhms.sweng.event_management.services;

import de.fhms.sweng.event_management.dto.EventTO;
import de.fhms.sweng.event_management.entities.Preference;
import de.fhms.sweng.event_management.exceptions.ResourceNotFoundException;
import de.fhms.sweng.event_management.repositories.PreferenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class PreferenceServiceTest {

    private Preference preference;
    private Preference p;
    private Set<Preference> preferenceSet;
    private LocalDateTime time;
    private EventTO event;
    private PreferenceService preferenceService;

    @Mock
    private PreferenceRepository preferenceRepository;

    @BeforeEach
    public void setUp() {

        preference = new Preference();
        preference.setId(1);
        preference.setValue("value");

        preferenceSet = new HashSet<Preference>();
        preferenceSet.add(preference);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String datetime = "2020-06-06 20:00";
        time = LocalDateTime.parse(datetime, formatter);

        event = new EventTO(1, 1, "name", "description",time , 5, 50.00, 50.00,preferenceSet);

        preferenceService = new PreferenceService(preferenceRepository);

        p = new Preference();

    }

    @Test
    void shouldGetPreferenceByValue() {
        given(preferenceRepository.findByValue("value")).willReturn(Optional.of(preference));
        Optional<Preference> optReturnedPreference = preferenceService.getPrefernceByValue("value");
        if (optReturnedPreference.isPresent()) {
            Preference returnedPreference = optReturnedPreference.get();
            assertEquals(preference, returnedPreference);
        }
        else {
            fail("no preference was returned");
        }
    }

    @Test
    void shouldCreatePreferencesFromEvent() {
        given(preferenceRepository.findByValue("value")).willReturn(Optional.of(preference));
        preferenceService.createPreferencesFromEvent(event);
        verify(preferenceRepository, times(2)).findByValue("value");
    }

}
