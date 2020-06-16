package de.fhms.sweng.event_management.services;

import de.fhms.sweng.event_management.dto.BusinessUserTO;
import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.exceptions.ResourceNotFoundException;
import de.fhms.sweng.event_management.repositories.BusinessUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BusinessUserServiceTest {

    @Mock
    private BusinessUserRepository userRepository;

    @Mock
    private MapperService mapperService;

    private BusinessUser businessUser;
    private BusinessUserService userService;

    @BeforeEach
    public void setUp() {
        businessUser = new BusinessUser();
        businessUser.setId(1);
        businessUser.setMail("tom@fhms.de");
        businessUser.setFirstName("Tom");
        businessUser.setLastName("Mustermann");

        userService = new BusinessUserService(userRepository, mapperService);
    }

    @Test
    void shouldGetBusinessUserById() {
        given(userRepository.findById(1)).willReturn(Optional.of(businessUser));
        BusinessUser returnedUser = this.userService.getBusinessUser(1);
        assertEquals(businessUser, returnedUser);
    }

    @Test
    void shouldGetBusinessUserByMail() {
        given(userRepository.findByMail("tom@fhms.de")).willReturn(Optional.of(businessUser));
        BusinessUser returnedUser = this.userService.getBusinessUser("tom@fhms.de");
        assertEquals(businessUser, returnedUser);
    }

    @Test
    void shouldCreateBusinessUser() {
        BusinessUserTO newUserTO = new BusinessUserTO();
        newUserTO.setId(2);
        newUserTO.setEmail("max@mustermann.de");
        newUserTO.setFirstName("Max");
        newUserTO.setLastName("Mustermann");

        BusinessUser newUser = new BusinessUser();
        newUser.setId(2);
        newUser.setMail("max@mustermann.de");
        newUser.setFirstName("Max");
        newUser.setLastName("Mustermann");

        given(mapperService.convertToUser(newUserTO)).willReturn(newUser);
        this.userService.createBusinessUser(newUserTO);
        verify(userRepository).save(newUser);

    }

    @Test
    void shouldNotGetBusinessUserById() {
        given(userRepository.findById(5)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getBusinessUser(5);
        });
    }

    @Test
    void shouldNotGetBusinessUserByMail() {
        given(userRepository.findByMail("diesdas")).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getBusinessUser("diesdas");
        });
    }

}
