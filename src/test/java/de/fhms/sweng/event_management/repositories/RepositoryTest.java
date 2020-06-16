package de.fhms.sweng.event_management.repositories;

import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.entities.Event;
import de.fhms.sweng.event_management.entities.Preference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Set;

@DataJpaTest
public class RepositoryTest {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    BusinessUserRepository userRepository;

    @Autowired
    PreferenceRepository preferenceRepository;

    @Test
    void shouldFindEventById() {
        Optional<Event> optEvent = eventRepository.findById(1);
        if (optEvent.isPresent()) {
            assertEquals(1, optEvent.get().getId());
        }
        else {
            fail("no event returned");
        }

    }

    @Test
    void shouldFindEventByName() {
        Set<Event> optEvent = eventRepository.findByName("Test Event");
        if (!(optEvent.isEmpty())) {
            for (Event e:optEvent) {
                assertEquals("Test Event", e.getName());
            }
        }
        else {
            fail("no event returned");
        }
    }

    @Test
    void shouldFindUserById() {
        Optional<BusinessUser> optUser = userRepository.findById(1);
        if (!(optUser.isEmpty())) {
            assertEquals(1, optUser.get().getId());
        }
        else {
            fail("no user returned");
        }
    }

    @Test
    void shouldFindUserByMail() {
        Optional<BusinessUser> optUser = userRepository.findByMail("tom@fhms.de");
        if (!(optUser.isEmpty())) {
            assertEquals("tom@fhms.de", optUser.get().getMail());
        }
        else {
            fail("no user returned");
        }
    }

    @Test
    void shouldFindPrefernceById() {
        Optional<Preference> optPreference = preferenceRepository.findById(1);
        if (!(optPreference.isEmpty())) {
            assertEquals(1, optPreference.get().getId());
        }
        else {
            fail("no preference returned");
        }
    }

    @Test
    void shouldFindPreferenceByValue() {
        Optional<Preference> optPreference = preferenceRepository.findByValue("Music");
        if(!(optPreference.isEmpty())) {
            assertEquals("Music", optPreference.get().getValue());
        }
        else {
            fail("no preference returned");
        }
    }

}
