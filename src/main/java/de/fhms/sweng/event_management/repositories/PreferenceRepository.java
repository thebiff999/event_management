package de.fhms.sweng.event_management.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import de.fhms.sweng.event_management.entities.Preference;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreferenceRepository extends CrudRepository<Preference, Integer> {

    @Query(value="select p from Preference p where p.value = ?1")
    Optional<Preference> findByName(String name);


}
