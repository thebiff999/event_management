package de.fhms.sweng.event_management.consumer;


import de.fhms.sweng.event_management.dto.BusinessUserTO;
import de.fhms.sweng.event_management.services.BusinessUserService;
import de.fhms.sweng.event_management.services.MapperService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {

    private BusinessUserService businessUserService;
    private MapperService mapperService;

    @Autowired
    public UserConsumer(BusinessUserService businessUserService, MapperService mapperService) {
        this.businessUserService = businessUserService;
        this.mapperService = mapperService;
    }


    @RabbitListener (queues = "${amqp.rabbitmq.queue.euser.save}")
        public void recieveNewBusinessUser(BusinessUserTO businessUserTO) {
        businessUserService.createBusinessUser(mapperService.convertToUser(businessUserTO));
    }


}

