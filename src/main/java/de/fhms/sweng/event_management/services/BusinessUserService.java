package de.fhms.sweng.event_management.services;

import de.fhms.sweng.event_management.clients.ClientService;
import de.fhms.sweng.event_management.dto.BusinessUserTO;
import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.exceptions.ResourceNotFoundException;
import de.fhms.sweng.event_management.repositories.BusinessUserRepository;
import org.mapstruct.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;

/**
 * Spring service that handles tasks related to BusinessUser entities
 * @author Dennis Heuermann
 */
@Service
public class BusinessUserService {

    private BusinessUserRepository businessUserRepository;
    private MapperService mapper;
    private ClientService clientService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * constructor with dependency injection for BusinessUserRepository and MapperService
     * @param businessUserRepository BusinessUser Repository
     * @param mapperService MapperService
     * @param clientService ClientService
     */
    @Autowired
    BusinessUserService(BusinessUserRepository businessUserRepository, MapperService mapperService, ClientService clientService) {
        this.businessUserRepository = businessUserRepository;
        this.mapper = mapperService;
        this.clientService = clientService;
    }

    /**
     * returns the user with the requested id
     * throws a ResourceNotFoundException if the requested user is not in the database
     * @param id search parameter
     * @return user with requested id
     */
    public BusinessUser getBusinessUser(int id) {

        Optional<BusinessUser> optionalBusinessUser = businessUserRepository.findById(id);
        if (optionalBusinessUser.isPresent()) {
            LOGGER.trace("returning BusinessUser with id {}", id);
            return optionalBusinessUser.get();
        }
        else {
            try {
                BusinessUserTO userTO = clientService.getUserById(id);
                BusinessUser user = mapper.convertToUser(userTO);
                return user;
            }
            catch (Throwable t) {
                    LOGGER.error("Business User with id {} could not be found", id);
                    throw new ResourceNotFoundException("Business User not found");
                }
            }
        }


    /**
     * looks for the user with the requested mail in the repository
     * also calls the User Management service via REST-API if the user is not in the repository
     * trhows a ResourceNotFoundException if the user doesn't exist there either
     * @param mail search parameter
     * @return user with requested mail
     */
    public BusinessUser getBusinessUser(String mail) {

        Optional<BusinessUser> optionalBusinessUser = businessUserRepository.findByMail(mail);
        if (optionalBusinessUser.isPresent()) {
            LOGGER.trace("returning BusinessUser with mail {}", mail);
            return optionalBusinessUser.get();
        }
        else {
            try {
                BusinessUserTO userTO = clientService.getUserByMail(mail);
                BusinessUser user = mapper.convertToUser(userTO);
                return user;
            }
            catch (Throwable t) {
                LOGGER.error("Business User with mail {} could not be found", mail);
                throw new ResourceNotFoundException("Business User not found");
            }
        }

    }

    /**
     * creates BusinessUser entity from Data Transfer Object and adds it to the repository
     * @param businessUserTO DTO object from which BusinessUser entity will be created
     */
    public void createBusinessUser(BusinessUserTO businessUserTO) {
        businessUserRepository.save(mapper.convertToUser(businessUserTO));
        LOGGER.info("Business User with id {} created", businessUserTO.getId());
    }

}
