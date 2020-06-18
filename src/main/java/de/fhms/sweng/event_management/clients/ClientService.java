package de.fhms.sweng.event_management.clients;

import de.fhms.sweng.event_management.dto.BusinessUserTO;
import de.fhms.sweng.event_management.dto.UserTO;
import de.fhms.sweng.event_management.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private LoginClient loginClient;

    private UserClient userClient;

    @Value("${login.email}")
    private String email;

    @Value("${login.password}")
    private String password;

    private final String CONTENT_TYPE = "application/json";
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private UserTO login;


    public ClientService(LoginClient loginClient, UserClient userclient) {
        this.loginClient = loginClient;
        this.userClient = userclient;
    }

    public BusinessUserTO getUserById(int id){
        login = new UserTO(email, password);
        LOGGER.debug("requesting jwt from login service with email: {} and password: {}", login.getEmail(), login.getPassword());
        String jwt = loginClient.loginUser(login);
        LOGGER.debug("JSON web token received by the login service: {}", jwt);
        jwt = "Authorization=" + jwt;
        BusinessUserTO userTO = userClient.getUserById(jwt,id);

        if (!(userTO == null)) {
            return userTO;
        }
        else {
            throw new ResourceNotFoundException("user could not be found");
        }

    }

    public BusinessUserTO getUserByMail(String mail){
        login = new UserTO(email, password);
        LOGGER.debug("requesting jwt from login service with email: {} and password: {}", login.getEmail(), login.getPassword());
        String jwt = loginClient.loginUser(login);
        LOGGER.debug("JSON web token received by the login service: {}", jwt);
        jwt = "Authorization=" + jwt;
        BusinessUserTO userTO = userClient.getUserByMail(jwt,mail);

        if (!(userTO == null)) {
            return userTO;
        }
        else {
            throw new ResourceNotFoundException("user could not be found");
        }
    }

}
