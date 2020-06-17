package de.fhms.sweng.event_management.repositories;

import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.entities.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * CRUD Repositroy for BusinessUser entities
 * @author Dennis Heuermann
 */
@Repository
public interface BusinessUserRepository extends CrudRepository<BusinessUser, Integer> {

    /**
     * returns BusinessUser entity by parameter 'mail'
     * @param name search parameter
     * @return Optional of BusinessUser
     */
    @Query(value="select b from BusinessUser b where b.mail = ?1")
    Optional<BusinessUser> findByMail(String name);

}
