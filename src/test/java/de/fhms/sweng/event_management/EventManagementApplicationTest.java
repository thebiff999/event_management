package de.fhms.sweng.event_management;

import de.fhms.sweng.event_management.consumer.UserConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
class EventManagementApplicationTest {

    @Test
    public void contextLoads() {
    }

}
