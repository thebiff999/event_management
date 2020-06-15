package de.fhms.sweng.event_management.consumer;

import de.fhms.sweng.event_management.dto.BusinessUserTO;
import de.fhms.sweng.event_management.producer.EventProducer;
import de.fhms.sweng.event_management.services.BusinessUserService;
import de.fhms.sweng.event_management.services.MapperService;
import org.apache.qpid.server.SystemLauncher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class UserConsumerTest {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static SystemLauncher systemLauncher = new SystemLauncher();
    private final static String EXCHANGE = "TestExchange";
    private final static String KEY = "TestRoutingKey";

    @Value("${amqp.rabbitmq.queue.euser.save}")
    private String queueString;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private MapperService mapperService;

    @SpyBean
    BusinessUserService userService;

    @BeforeEach
    public void startBroker() throws Exception {

        if (queueString==null) {queueString = "gapp.test.queue.event.user";}
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
        DirectExchange exchange = new DirectExchange(EXCHANGE);
        admin.declareExchange(exchange);
        LOGGER.debug("creating and binding queue");
        Queue queue = new Queue(this.queueString, true);
        admin.declareQueue(queue);
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(KEY));
        cf.destroy();

    }


    @AfterEach
    public void stopBroker() {
        systemLauncher.shutdown();
    }

    @Test
    void shouldReceiveUser() throws Exception {
        BusinessUserTO user = new BusinessUserTO();
        user.setId(1);
        user.setEmail("tom@fhms.de");
        user.setFirstName("Tom");
        user.setLastName("Mustermann");
        doReturn(null).when(userService).createBusinessUser(mapperService.convertToUser(user));
        amqpTemplate.convertAndSend(EXCHANGE, KEY, user);
        Thread.sleep(5000);
        verify(userService).createBusinessUser(mapperService.convertToUser(user));
    }



}
