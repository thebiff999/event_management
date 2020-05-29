package de.fhms.sweng.event_management.consumer;


import de.fhms.sweng.event_management.services.BusinessUserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {

    private BusinessUserService businessUserService;

    @Autowired
    public UserConsumer(BusinessUserService businessUserService) {
        this.businessUserService = businessUserService;
    }

    /*
    @RabbitListener (queues = "${amqp.rabbitmq.queue.euser.save}")
        public void recieveNewBusinessUser(UserRabbitTO userTO) {

    }
    */

}

