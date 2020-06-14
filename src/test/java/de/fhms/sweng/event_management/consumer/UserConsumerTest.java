package de.fhms.sweng.event_management.consumer;

import de.fhms.sweng.event_management.dto.BusinessUserTO;
import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.repositories.BusinessUserRepository;
import de.fhms.sweng.event_management.services.BusinessUserService;
import org.junit.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserConsumerTest {

    String exchange = "gapp.test.direct.serv.event";
    String key = "gapp.test.event.user";

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    BusinessUserRepository repo;

    private BusinessUserTO user;

    @BeforeEach
    void setUp() {
        user = new BusinessUserTO();
        user.setId(10);
        user.setFirstName("first");
        user.setLastName("last");
        user.setEmail("first@last.de");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void recieveNewBusinessUser() {
        assertEquals(1,1);
        amqpTemplate.convertAndSend(exchange, key, user);
        //Thread.sleep(5000);
        assertNotNull(repo.findById(10).get());
    }
}