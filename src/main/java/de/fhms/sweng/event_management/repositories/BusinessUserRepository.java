package de.fhms.sweng.event_management.repositories;

import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.entities.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface BusinessUserRepository extends CrudRepository<BusinessUser, Integer> {

    @Query(value="select b from BusinessUser b where b.mail = ?1")
    Optional<BusinessUser> findByMail(String name);

}
