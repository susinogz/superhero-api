package com.jesusfernandez.superheroapi.repository;

import com.jesusfernandez.superheroapi.model.entity.SuperheroEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuperheroRepository extends CrudRepository<SuperheroEntity, Long> {

    /**
     * Lists all superheroes which contains coincidences with provided word in his name
     *
     * @param word Provided word
     * @return All coincidences in database
     */
    @Query(
            value = "SELECT * FROM Superhero WHERE LOWER(Name) LIKE CONCAT('%', LOWER(:word), '%')",
            nativeQuery = true
    )
    List<SuperheroEntity> findCoincidencesInName(@Param("word") String word);

    /**
     * List all superheroes in database
     *
     * @return List with all superheroes
     */
    @Override
    List<SuperheroEntity> findAll();

    /**
     * Finds superhero en database by name
     *
     * @param name Provided name
     * @return Found superhero
     */
    Optional<SuperheroEntity> findByName(String name);
}
