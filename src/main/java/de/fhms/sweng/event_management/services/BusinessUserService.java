package de.fhms.sweng.event_management.services;

import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.exceptions.ResourceNotFoundException;
import de.fhms.sweng.event_management.repositories.BusinessUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;

@Service
public class BusinessUserService {

    private BusinessUserRepository businessUserRepository;

    @Autowired
    BusinessUserService(BusinessUserRepository businessUserRepository) {
        this.businessUserRepository = businessUserRepository;
    }

    BusinessUser getBusinessUser(int id) {

        Optional<BusinessUser> optionalBusinessUser = businessUserRepository.findById(id);
        if (optionalBusinessUser.isPresent()) {
            return optionalBusinessUser.get();
        }
        else throw new ResourceNotFoundException("Business User not found");

    }

    public void createBusinessUser(BusinessUser businessUser) {

        businessUserRepository.save(businessUser);

    }

}
