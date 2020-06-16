package de.fhms.sweng.event_management.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessUserTOTest {

    private BusinessUserTO user;
    private BusinessUserTO equalUser;
    private BusinessUserTO differentUser;

    @BeforeEach
    void setUp() {
        user = new BusinessUserTO();
        user.setId(1);
        user.setFirstName("first");
        user.setLastName("last");
        user.setEmail("first@last.de");

        equalUser = new BusinessUserTO();
        equalUser.setId(1);
        equalUser.setFirstName("first");
        equalUser.setLastName("last");
        equalUser.setEmail("first@last.de");

        differentUser = new BusinessUserTO();
        differentUser.setId(2);
        differentUser.setFirstName("Max");
        differentUser.setLastName("Mustermann");
        differentUser.setEmail("max@mustermann.de");
    }

    @Test
    void testToString() {
        String string = "BusinessUserTO" + "\n"
                + "id: " + "1" + "\n"
                + "firstName: " + "first" + "\n"
                + "lastName: " + "last" + "\n"
                + "email: " + "first@last.de" + "\n";
        assertEquals(string, user.toString());
    }

    @Test
    void testHashCodeTrue() {
        assertEquals(user.hashCode(), equalUser.hashCode());
    }

    @Test
    void testHashCodeFalse() {
        assertNotEquals(user.hashCode(), differentUser.hashCode());
    }

    @Test
    void testHashCodeSelf() {
        assertEquals(user.hashCode(), user.hashCode());
    }

    @Test
    void getId() {
        assertEquals(Integer.valueOf(1), user.getId());
    }

    @Test
    void setId() {
        user.setId(20);
        assertEquals(Integer.valueOf(20), user.getId());
    }

    @Test
    void getFirstName() {
        assertEquals("first", user.getFirstName());
    }

    @Test
    void setFirstName() {
        user.setFirstName("changed");
        assertEquals("changed", user.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("last", user.getLastName());
    }

    @Test
    void setLastName() {
        user.setLastName("changed");
        assertEquals("changed", user.getLastName());
    }

    @Test
    void getEmail() {
        assertEquals("first@last.de", user.getEmail());
    }

    @Test
    void setEmail() {
        user.setEmail("changed@test.de");
        assertEquals("changed@test.de", user.getEmail());
    }
}