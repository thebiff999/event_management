package de.fhms.sweng.event_management.consumer;


import de.fhms.sweng.event_management.dto.BusinessUserTO;
import de.fhms.sweng.event_management.services.BusinessUserService;
import de.fhms.sweng.event_management.services.MapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class receives new users and tells the business user service to add them in the database
 * @author: Dennis Heuermann
 */
@Component
public class UserConsumer {

    private BusinessUserService businessUserService;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * standard constructor
     * @param businessUserService
     */
    @Autowired
    public UserConsumer(BusinessUserService businessUserService) {
        this.businessUserService = businessUserService;
    }

    /**
     *
     * @param businessUserTO the business user dto to add to the database
     */
    @RabbitListener (queues = "${amqp.rabbitmq.queue.euser.save}")
        public void recieveNewBusinessUser(BusinessUserTO businessUserTO) {
        LOGGER.info("recieved message with new user with id {}", businessUserTO.getId());
        LOGGER.debug("Values of businessUserTO:");
        LOGGER.debug("{}", businessUserTO.toString());
        businessUserService.createBusinessUser(businessUserTO);
    }


}

