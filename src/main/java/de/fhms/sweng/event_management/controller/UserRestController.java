package de.fhms.sweng.event_management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.fhms.sweng.event_management.clients.ClientService;
import de.fhms.sweng.event_management.dto.BusinessUserTO;
import de.fhms.sweng.event_management.dto.EventTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Rest Controller for accessing the clientservice methods, only in active in dev environment for testing purposes
 */
@RestController
@Profile("dev")
@RequestMapping("/user")
public class UserRestController {

    private ClientService clientService;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/byMail")
    public BusinessUserTO getUserByMai(@RequestParam(value="mail")String email) throws JsonProcessingException {
        LOGGER.info("GET-Request on /byMail received with parameter {}", email);
        return clientService.getUserByMail(email);
    }


    @GetMapping("/byId")
    public BusinessUserTO getUserById(@RequestParam(value="id")int id) throws JsonProcessingException {
        LOGGER.info("GET-Request on /byId received with parameter {}", id);
        return clientService.getUserById(id);
    }

}
