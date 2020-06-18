package de.fhms.sweng.event_management.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTOTest {

    private UserTO userTO;

    @BeforeEach
    public void setUp() {
      userTO = new UserTO();
      userTO.setEmail("test-mail");
      userTO.setPassword("test-pwd");
    }


    @Test
    void EmptyConstructorTest() {
        UserTO userTO = new UserTO();
        assertNotEquals(null, userTO);
    }

    @Test
    void ConstructorTest() {
        UserTO userTO = new UserTO("mail", "password");
        assertEquals("mail", userTO.getEmail());
        assertEquals("password", userTO.getPassword());
    }

    @Test
    void setMailTest() {
        userTO.setEmail("diff");
        assertEquals("diff", userTO.getEmail());
    }

    @Test
    void getMailTest() {
        assertEquals("test-mail", userTO.getEmail());
    }

    @Test
    void setPasswordTest() {
        userTO.setPassword("diff-pwd");
        assertEquals("diff-pwd", userTO.getPassword());
    }

    @Test
    void getPasswordTest() {
        assertEquals("test-pwd", userTO.getPassword());
    }
}
