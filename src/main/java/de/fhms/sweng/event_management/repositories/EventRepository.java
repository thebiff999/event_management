package de.fhms.sweng.event_management.repositories;


import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.entities.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

/**
 * CRUD Repository for Event entities
 * @author Dennis Heuermann
 */
@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {

    /**
     * returns a Set of Event entities by a name parameter
     * @param name search parameter
     * @return set of events
     */
    @Query(value="select e from Event e where e.name like ?1")
    Set<Event> findByName(String name);

    /**
     * returnes a Set of Event entities by a requested User
     * @param businessUserId search parameter
     * @return set of events by one user
     */
    @Query(value="select e from Event e where e.businessUserId = ?1")
    Set<Event> findAllByUserId(BusinessUser businessUserId);

    /**
     * returns a Set of Event entities that map the requested preference
     * @param preference search parameter
     * @return set of events that map the parameter
     */
    @Query(value="select e from Event e where e.preferences =?1")
    Set<Event> findallByPreference(String preference);
}
