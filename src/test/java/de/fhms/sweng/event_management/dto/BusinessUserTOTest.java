package de.fhms.sweng.event_management.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessUserTOTest {

    private BusinessUserTO user;

    @BeforeEach
    void setUp() {
        user = new BusinessUserTO();
        user.setId(1);
        user.setFirstName("first");
        user.setLastName("last");
        user.setEmail("first@last.de");
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
    void testHashCode() {
        BusinessUserTO user2 = new BusinessUserTO();
        BusinessUserTO user3 = new BusinessUserTO();

        user2.setId(1);
        user2.setFirstName("first");
        user2.setLastName("last");
        user2.setEmail("first@last.de");

        user3.setId(2);
        user3.setFirstName("first");
        user3.setLastName("last");
        user3.setEmail("first@last.de");

        assertEquals(user.hashCode(), user2.hashCode());
        assertNotEquals(user.hashCode(), user3.hashCode());
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