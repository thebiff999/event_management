package de.fhms.sweng.event_management.repositories;

import de.fhms.sweng.event_management.entities.BusinessUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessUserRepository extends CrudRepository<BusinessUser, Integer> {

}
