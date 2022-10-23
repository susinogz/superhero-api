package com.jesusfernandez.superheroapi.service;

import com.jesusfernandez.superheroapi.mapper.SuperheroMapper;
import com.jesusfernandez.superheroapi.model.dao.SuperheroDAO;
import com.jesusfernandez.superheroapi.model.dto.SuperheroDTO;
import com.jesusfernandez.superheroapi.repository.SuperheroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    //TODO find by name and check if it exists
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SuperheroDTO insertSuperhero(SuperheroDTO superhero) {
        SuperheroDAO superheroDAO = mapper.toDao(superhero);
        log.info("Inserting new superhero: {}", superheroDAO);
        SuperheroDAO superheroInserted = repository.save(superheroDAO);
        log.info("Successfully insertion. Superhero: {}", superheroInserted);
        return mapper.toDto(superheroInserted);
    }

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
