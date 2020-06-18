package de.fhms.sweng.event_management.clients;

import de.fhms.sweng.event_management.dto.BusinessUserTO;
import de.fhms.sweng.event_management.dto.UserTO;
import de.fhms.sweng.event_management.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private LoginClient loginClient;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private ClientService clientService;

    private UserTO userTO;
    private BusinessUserTO returnedUser;
    private String email;
    private String password;

    @BeforeEach
    public void setUp() {
        email = "alex@fhms.de";
        password = "alex1";
        clientService = new ClientService(loginClient, userClient);
        ReflectionTestUtils.setField(clientService, "email", "alex@fhms.de");
        ReflectionTestUtils.setField(clientService, "password", "alex1");
        userTO = new UserTO(email, password);
        returnedUser = new BusinessUserTO();
        returnedUser.setId(1);
        returnedUser.setFirstName("Max");
        returnedUser.setLastName("Mustermann");
        returnedUser.setEmail("max@mustermann.de");
    }

    @Test
    void shouldGetUserById() {
        given(loginClient.loginUser(refEq(userTO))).willReturn("jwt-token");
        given(userClient.getUserById("Authorization=jwt-token",1)).willReturn(returnedUser);
        assertEquals(returnedUser, clientService.getUserById(1));
    }

    @Test
    void shouldNotGetUserById() {
        given(loginClient.loginUser(refEq(userTO))).willReturn("jwt-token");
        given(userClient.getUserById("Authorization=jwt-token",1)).willReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            clientService.getUserById(1);
        });
    }

    @Test
    void shouldGetUserByMail() {
        given(loginClient.loginUser(refEq(userTO))).willReturn("jwt-token");
        given(userClient.getUserByMail("Authorization=jwt-token","max@mustermann.de")).willReturn(returnedUser);
        assertEquals(returnedUser, clientService.getUserByMail("max@mustermann.de"));
    }

    @Test
    void shouldNotGetUserByMail() {
        given(loginClient.loginUser(refEq(userTO))).willReturn("jwt-token");
        given(userClient.getUserByMail("Authorization=jwt-token","max@mustermann.de")).willReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> {
            clientService.getUserByMail("max@mustermann.de");
        });
    }

}
