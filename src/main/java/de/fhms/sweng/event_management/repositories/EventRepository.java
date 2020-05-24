package de.fhms.sweng.event_management.repositories;


import de.fhms.sweng.event_management.entities.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {

    @Query(value="select e from Event e where e.name like ?1")
    Set<Event> findByName(String name);

    @Query(value="select e from Event e where e.businessUserId = ?1")
    Set<Event> findAllByUserId(int id);

    @Query(value="select e from Event e where e.preferences =?1")
    Set<Event> findallByPreference(String preference);
}
