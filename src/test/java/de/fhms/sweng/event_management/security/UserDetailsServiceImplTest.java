package de.fhms.sweng.event_management.security;

import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.repositories.BusinessUserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    private String username;
    private BusinessUser user;
    private UserDetailsServiceImpl service;
    private UserDetails expected;

    @Mock
    private BusinessUserRepository userRepository;

    @BeforeAll
    public void setUp() {
        username = "max@mustermann.de";
        service = new UserDetailsServiceImpl(userRepository);
        user = new BusinessUser();
        user.setFirstName("max");
        user.setLastName("mustermann");
        user.setId(1);
        user.setMail(username);

        expected = User.withUsername(username).password("***").authorities("EUSER").build();
    }

    @Test
    void loadUserByName() {
        given(userRepository.findByMail(username)).willReturn(Optional.of(user));
        UserDetails result = service.loadUserByUsername(username);
        assertEquals(expected, result);
    }

    @Test
    void loadUserByNameFailure() {
        given(userRepository.findByMail(username)).willReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,() -> {
            service.loadUserByUsername(username);
        });
        }

}
