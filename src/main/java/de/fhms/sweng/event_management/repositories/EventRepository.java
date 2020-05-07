package de.fhms.sweng.event_management.repositories;


import de.fhms.sweng.event_management.entities.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
}
