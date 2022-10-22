package com.jesusfernandez.superheroapi.repository;

import com.jesusfernandez.superheroapi.model.dao.SuperheroDAO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuperheroRepository extends CrudRepository<SuperheroDAO, Long> {

    @Query(
            value = "SELECT * FROM Superhero WHERE LOWER(Name) LIKE CONCAT('%', LOWER(:word), '%')",
            nativeQuery = true
    )
    List<SuperheroDAO> findCoincidencesInName(@Param("word") String word);

    @Override
    List<SuperheroDAO> findAll();

    Optional<SuperheroDAO> findByName(String name);
}
