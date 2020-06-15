package de.fhms.sweng.event_management.producer;

import de.fhms.sweng.event_management.dto.EventTO;
import org.apache.qpid.server.SystemLauncher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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


        private static SystemLauncher systemLauncher = new SystemLauncher();
        private boolean brokerRunning;
        private static final String QUEUE_NAME1 = "event.save";
        private static final String QUEUE_NAME2 = "event.delete";

        @Value("${amqp.rabbitmq.exchange}")
        private String exchange;

        @Value("${amqp.rabbitmq.routingkey.event.save}")
        private String rKeySave;

        @Value("$amqp.rabbitmq.routingkey.event.delete")
        private String rKeyDel;

        @Autowired
        private AmqpTemplate amqpTemplate;

        @Autowired
        private EventProducer eventProducer;

        private EventTO event;

        @BeforeEach
        public void startBroker() throws Exception {
            if (!brokerRunning) {
                Map<String, Object> attributes = new HashMap<>();
                URL initialConfig = EventProducer.class.getClassLoader().getResource("initial-config.json");
                attributes.put("initialConfigurationLocation", initialConfig.toExternalForm());
                attributes.put("type", "Memory");
                attributes.put("startupLoggedToSystemOut", true);
                attributes.put("qpid.amqp_port", "5672");
                systemLauncher.startup(attributes);
                brokerRunning=true;

                CachingConnectionFactory cf = new CachingConnectionFactory("localhost", 5672);
                AmqpAdmin admin = new RabbitAdmin(cf);
                DirectExchange exchange = new DirectExchange(this.exchange);
                admin.declareExchange(exchange);
                Queue queue1 = new Queue(QUEUE_NAME1, true);
                admin.declareQueue(queue1);
                admin.declareBinding(BindingBuilder.bind(queue1).to(exchange).with(this.rKeySave));
                Queue queue2 = new Queue(QUEUE_NAME2, true);
                admin.declareQueue(queue2);
                admin.declareBinding(BindingBuilder.bind(queue2).to(exchange).with(this.rKeyDel));
                cf.destroy();
            }

            event = new EventTO();

        }

        @AfterAll
        public static void stopBroker() {
            systemLauncher.shutdown();
        }


        @Test
        void shouldSendNewEvent() throws Exception {
            eventProducer.sendNewEvent(event);
            EventTO receivedEvent = (EventTO) amqpTemplate.receiveAndConvert(QUEUE_NAME1, 5000);
            assertNotNull(receivedEvent);
        }

        @Test
        void shouldSendDeletedEvent() throws Exception {
            eventProducer.sendDeletedEvent(event);
            EventTO deletedEvent = (EventTO) amqpTemplate.receiveAndConvert(QUEUE_NAME2, 5000);
            assertNotNull(deletedEvent);
        }

    }
