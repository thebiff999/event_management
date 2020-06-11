package de.fhms.sweng.event_management.consumer;

import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.services.BusinessUserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserConsumerTest {

    @Value("${spring.rabbitmq.username}")
    String test;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void recieveNewBusinessUser() {
        System.out.println("Test der properties");
        System.out.println(test);
    }
}