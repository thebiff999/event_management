package de.fhms.sweng.event_management.clients;

import de.fhms.sweng.event_management.dto.BusinessUserTO;
import de.fhms.sweng.event_management.dto.UserTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private LoginClient loginClient;

    @Autowired
    private UserClient userClient;

    @Value("${login.email}")
    private String email;

    @Value("${login.password}")
    private String password;

    private final String CONTENT_TYPE = "application/json";
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private UserTO login;


    public ClientService() {
    }

    public BusinessUserTO getUserById(int id){
        LOGGER.debug("value for email: {}", email);
        LOGGER.debug("value for password: {}", password);
        login = new UserTO(email, password);
        LOGGER.debug("requesting jwt from login service with email: {} and password: {}", login.getEmail(), login.getPassword());
        String jwt = loginClient.loginUser(login);
        BusinessUserTO userTO = userClient.getUserById(jwt,id);
        return userTO;
    }

    public BusinessUserTO getUserByMail(String email){
        LOGGER.debug("requesting jwt from login service with email: {} and password: {}", email, password);
        login = new UserTO(email, password);
        String jwt = loginClient.loginUser(login);
        BusinessUserTO userTO = userClient.getUserByMail(jwt,email);
        return userTO;
    }

}
