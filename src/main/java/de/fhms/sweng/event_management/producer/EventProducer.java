package de.fhms.sweng.event_management.producer;

import de.fhms.sweng.event_management.dto.EventTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Spring Component that sends messages to configurable amqp queues when an event is created or deleted
 * @author Dennis Heuermann
 */
@Component
public class EventProducer {

    private AmqpTemplate amqpTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public EventProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Value("${amqp.rabbitmq.exchange}")
    private String exchange;

    @Value("${amqp.rabbitmq.routingkey.event.save}")
    private String newEventKey;

    @Value("${amqp.rabbitmq.routingkey.event.delete}")
    private String deletedEventKey;


    /**
     * send an event data transfer object of a newly created event to the configured queue
     * @param event event dto of new event
     */
    public void sendNewEvent(EventTO event) {
        amqpTemplate.convertAndSend(exchange, newEventKey, event);
        LOGGER.info("Message sent with content: {}" ,event.toString());
    }

    /**
     * send an event data transfer object of a deleted event to the configured queue
     * @param event event dto of deleted event
     */
    public void sendDeletedEvent(EventTO event) {
        amqpTemplate.convertAndSend(exchange, deletedEventKey, event);
        LOGGER.info("Message sent with content: {}", event.toString());
    }


}
