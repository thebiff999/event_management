package de.fhms.sweng.event_management.producer;

import de.fhms.sweng.event_management.dto.EventTO;
import org.apache.qpid.server.SystemLauncher;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;


import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
public class EventProducerTest {


        private final Logger LOGGER = LoggerFactory.getLogger(getClass());
        private static SystemLauncher systemLauncher = new SystemLauncher();
        private boolean brokerRunning;
        private static final String QUEUE_NAME1 = "event.save";
        private static final String QUEUE_NAME2 = "event.delete";

        @Value("${amqp.rabbitmq.exchange}")
        private String exchangeString;

        @Value("${amqp.rabbitmq.routingkey.event.save}")
        private String rKeySave;

        @Value("${amqp.rabbitmq.routingkey.event.delete}")
        private String rKeyDel;

        @Autowired
        private AmqpTemplate amqpTemplate;

        @Autowired
        private EventProducer eventProducer;

        @BeforeEach
        public void startBroker() throws Exception {

            LOGGER.debug("starting broker");

            Map<String, Object> attributes = new HashMap<>();
            URL initialConfig = EventProducer.class.getClassLoader().getResource("initial-config.json");
            attributes.put("initialConfigurationLocation", initialConfig.toExternalForm());
            attributes.put("type", "Memory");
            attributes.put("startupLoggedToSystemOut", true);
            attributes.put("qpid.amqp_port", "5672");
            systemLauncher.startup(attributes);

            CachingConnectionFactory cf = new CachingConnectionFactory("localhost", 5672);
            AmqpAdmin admin = new RabbitAdmin(cf);
            LOGGER.debug("creating exhcange");
            DirectExchange exchange = new DirectExchange(exchangeString);
            admin.declareExchange(exchange);
            LOGGER.debug("creating and binding queue1");
            Queue queue1 = new Queue(QUEUE_NAME1, true);
            admin.declareQueue(queue1);
            admin.declareBinding(BindingBuilder.bind(queue1).to(exchange).with(rKeySave));
            LOGGER.debug("creating and binding queue2");
            Queue queue2 = new Queue(QUEUE_NAME2, true);
            admin.declareQueue(queue2);
            admin.declareBinding(BindingBuilder.bind(queue2).to(exchange).with(rKeyDel));
            cf.destroy();

        }


        @AfterEach
        public void stopBroker() {
            systemLauncher.shutdown();
        }


        @Test
        void shouldSendNewEvent() throws Exception {
            EventTO newEvent = new EventTO();
            eventProducer.sendNewEvent(newEvent);
            EventTO receivedEvent = (EventTO) amqpTemplate.receiveAndConvert(QUEUE_NAME1, 10000);
            assertNotNull(receivedEvent);
        }

        @Test
        void shouldSendDeletedEvent() throws Exception {
            EventTO deletedEvent = new EventTO();
            eventProducer.sendDeletedEvent(deletedEvent);
            EventTO receivedEvent = (EventTO) amqpTemplate.receiveAndConvert(QUEUE_NAME2, 10000);
            assertNotNull(receivedEvent);
        }

    }
