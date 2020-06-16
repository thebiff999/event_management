package de.fhms.sweng.event_management.services;

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

@Service
public class BusinessUserService {

    private BusinessUserRepository businessUserRepository;
    private MapperService mapper;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    BusinessUserService(BusinessUserRepository businessUserRepository, MapperService mapperService) {
        this.businessUserRepository = businessUserRepository;
        this.mapper = mapperService;
    }

    public BusinessUser getBusinessUser(int id) {

        Optional<BusinessUser> optionalBusinessUser = businessUserRepository.findById(id);
        if (optionalBusinessUser.isPresent()) {
            LOGGER.trace("returning BusinessUser with id {}", id);
            return optionalBusinessUser.get();
        }
        else {
            LOGGER.error("Business User with id {} could not be found", id);
            throw new ResourceNotFoundException("Business User not found");
        }

    }

    public BusinessUser getBusinessUser(String mail) {

        Optional<BusinessUser> optionalBusinessUser = businessUserRepository.findByMail(mail);
        if (optionalBusinessUser.isPresent()) {
            LOGGER.trace("returning BusinessUser with mail {}", mail);
            return optionalBusinessUser.get();
        }
        else {
            LOGGER.error("Business User with mail {} could not be found", mail);
            throw new ResourceNotFoundException("Business User not found");
        }

    }

    public void createBusinessUser(BusinessUserTO businessUserTO) {
        businessUserRepository.save(mapper.convertToUser(businessUserTO));
        LOGGER.info("Business User with id {} created", businessUserTO.getId());
    }

}
