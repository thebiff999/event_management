package de.fhms.sweng.event_management.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessUserTest {

    BusinessUser user;

    @BeforeEach
    void setUp() {
        user = new BusinessUser();
        user.setFirstName("first");
        user.setLastName("last");
        user.setMail("first@last.de");
        user.setId(10);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void getId() {
        assertEquals(10, user.getId());
    }

    @Test
    void setId() {
        user.setId(20);
        assertEquals(20, user.getId());
    }

    @Test
    void getFirstName() {
        assertEquals("first", user.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("last", user.getLastName());
    }

    @Test
    void setFirstName() {
        user.setFirstName("different");
        assertEquals("different", user.getFirstName());
    }

    @Test
    void setLastName() {
        user.setLastName("different");
        assertEquals("different", user.getLastName());
    }

    @Test
    void getMail() {
        assertEquals("first@last.de", user.getMail());
    }

    @Test
    void setMail() {
        user.setMail("different@different.de");
        assertEquals("different@different.de", user.getMail());
    }

    @Test
    void getRole() {
        assertEquals("EUSER", user.getRole());
    }
}