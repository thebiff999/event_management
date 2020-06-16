package de.fhms.sweng.event_management.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessUserTest {

    BusinessUser user;
    BusinessUser equalUser;
    BusinessUser differentUser;


    @BeforeEach
    void setUp() {
        user = new BusinessUser();
        user.setFirstName("first");
        user.setLastName("last");
        user.setMail("first@last.de");
        user.setId(10);

        equalUser = new BusinessUser();
        equalUser.setFirstName("first");
        equalUser.setLastName("last");
        equalUser.setMail("first@last.de");
        equalUser.setId(10);

        differentUser = new BusinessUser();
        differentUser.setFirstName("Max");
        differentUser.setLastName("Mustermann");
        differentUser.setMail("max@mustermann.de");
        differentUser.setId(20);
    }


    @Test
    void testHashCodePositive() {
        int hash1 = user.hashCode();
        int hash2 = equalUser.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    void testHashCodeNegative() {
        int hash1 = user.hashCode();
        int hash2 = differentUser.hashCode();
        assertNotEquals(hash1, hash2);
    }

    @Test
    void testHashCodeSelf() {
        int hash1 = user.hashCode();
        int hash2 = user.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    void testEqualsPositive() {
        assertTrue(equalUser.equals(user));
    }

    @Test
    void testEqualsNegative() {
        assertFalse(differentUser.equals(user));
    }

    @Test
    void testEqualsSelf() {
        assertTrue(user.equals(user));
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