package com.jesusfernandez.superheroapi.service;

import com.jesusfernandez.superheroapi.mapper.SuperheroMapper;
import com.jesusfernandez.superheroapi.model.dao.SuperheroDAO;
import com.jesusfernandez.superheroapi.model.dto.SuperheroDTO;
import com.jesusfernandez.superheroapi.repository.SuperheroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = {"Superheroes", "SuperheroesCoincidences"})
@Service
@Slf4j
public class SuperheroService {

    private final SuperheroMapper mapper;
    private final SuperheroRepository repository;

    public SuperheroService(@Autowired SuperheroMapper mapper,
                            @Autowired SuperheroRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    /**
     * Lists all superheroes in database
     *
     * @return List with all Superheroes in database
     */
    @Cacheable(value = "Superheroes")
    @Transactional(readOnly = true)
    public List<SuperheroDTO> listSuperheroes() {
        log.info("Listing all superheroes in database");
        List<SuperheroDAO> superheroes = repository.findAll();
        if (superheroes.isEmpty()) {
            log.info("No superheroes found in the database");
            return Collections.emptyList();
        }
        log.info("Superheroes found: {}", superheroes);
        return superheroes.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    /**
     * Find a specific superhero in database by his id
     *
     * @param id Provided Superhero id
     * @return An Optional which contains superhero dto for the provided id
     */
    @Cacheable(key = "#id", cacheNames = "Superheroes")
    @Transactional(readOnly = true)
    public Optional<SuperheroDTO> getSuperhero(Long id) {
        log.info("Searching superhero by Id: {}", id);
        Optional<SuperheroDAO> superhero = repository.findById(id);
        if (superhero.isEmpty()) {
            log.info("No superhero found for Id: {}", id);
            return Optional.empty();
        }
        log.info("Superhero found: {}", superhero.get());
        return superhero.map(mapper::toDto);
    }

    /**
     * Lists all superheroes which contains coincidences with provided word in his name
     *
     * @param word Provide word
     * @return List with all coincidences
     */
    @Cacheable(key = "#word", cacheNames = "SuperheroesCoincidences")
    @Transactional(readOnly = true)
    public List<SuperheroDTO> getSuperheroes(String word) {
        log.info("Searching superheroes with coincidences for word: {}", word);
        List<SuperheroDAO> superheroes = repository.findCoincidencesInName(word);
        if (superheroes.isEmpty()) {
            log.info("No superheroes found in the database");
            return Collections.emptyList();
        }
        log.info("Superheroes found: {}", superheroes);
        return mapper.toDto(superheroes);
    }

    /**
     * Inserts provided superhero into database
     *
     * @param superhero Provided superhero
     * @return Updated superhero
     */
    @Cacheable(cacheNames = "Superheroes")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SuperheroDTO insertSuperhero(SuperheroDTO superhero) {
        SuperheroDAO superheroDAO = mapper.toDao(superhero);
        log.info("Inserting new superhero: {}", superheroDAO);
        SuperheroDAO superheroInserted = repository.save(superheroDAO);
        log.info("Successfully insertion. Superhero: {}", superheroInserted);
        return mapper.toDto(superheroInserted);
    }

    /**
     * Finds provided superhero by name in database and if exists updates it
     *
     * @param superhero Provided superhero to be updated
     * @return Updated superhero
     */
    @CachePut(key = "#id", cacheNames = "Superheroes")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<SuperheroDTO> updateSuperhero(Long id, SuperheroDTO superhero) {
        log.info("Updating superhero with Id: {}, Data: {}", superhero.getId(), superhero);
        Optional<SuperheroDAO> oldSuperhero = repository.findById(id);
        if (oldSuperhero.isEmpty()) {
            log.info("No superhero found for id: {}", id);
            return Optional.empty();
        }
        SuperheroDAO newSuperhero = mapper.toDao(superhero);
        newSuperhero.setId(oldSuperhero.get().getId());
        SuperheroDAO savedSuperhero = repository.save(newSuperhero);
        log.info("Successfully updated");
        return Optional.of(mapper.toDto(savedSuperhero));
    }

    /**
     * Deletes superhero in database for provided id
     *
     * @param id Provided id
     * @return Deleted superhero
     */
    @CacheEvict(key = "#id", cacheNames = "Superheroes")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<SuperheroDTO> deleteSuperheroById(Long id) {
        log.info("Deleting superhero with Id: {}", id);
        Optional<SuperheroDAO> superhero = repository.findById(id);
        if (superhero.isPresent()) {
            repository.deleteById(id);
            log.info("Successfully deleted");
            return superhero.map(mapper::toDto);
        }
        log.info("Not found superhero for Id: {}", id);
        return Optional.empty();
    }

}
