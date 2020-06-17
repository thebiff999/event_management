package de.fhms.sweng.event_management.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import de.fhms.sweng.event_management.entities.Preference;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * CRUD Repository for Preference entities
 * @author Dennis Heuermann
 */
@Repository
public interface PreferenceRepository extends CrudRepository<Preference, Integer> {

    /**
     * returns an Optional of Preference by searching for a preference value
     * @param value search parameter
     * @return Optional of Preference entitiy
     */
    @Query(value="select p from Preference p where p.value = ?1")
    Optional<Preference> findByValue(String value);


}
