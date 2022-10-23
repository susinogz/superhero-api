package com.jesusfernandez.superheroapi.controller;

import com.jesusfernandez.superheroapi.model.dto.SuperheroDTO;
import com.jesusfernandez.superheroapi.service.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(
        path = "superhero",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Slf4j
@Validated
public class Controller {

    private final SuperheroService superheroService;

    public Controller(@Autowired SuperheroService superheroService) {
        this.superheroService = superheroService;
    }

    @GetMapping(path = "list")
    public ResponseEntity<List<SuperheroDTO>> listSuperheroes() {
        log.info("Request received to list all superheroes");
        List<SuperheroDTO> superheroes = superheroService.listSuperheroes();
        if (superheroes.isEmpty()) {
            log.info("Request finished with status 404");
            return ResponseEntity.notFound().build();
        }
        log.info("Request finished with status 200");
        return ResponseEntity.ok(superheroes);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<SuperheroDTO> getSuperhero(@PathVariable @PositiveOrZero Long id) {
        log.info("Request received to get superhero with Id: {}", id);
        Optional<SuperheroDTO> superhero = superheroService.getSuperhero(id);
        if (superhero.isEmpty()) {
            log.info("Request finished with status 404");
            return ResponseEntity.notFound().build();
        }
        log.info("Request finished with status 200");
        return ResponseEntity.ok(superhero.get());
    }

    @GetMapping(path = "list/{word}")
    public ResponseEntity<List<SuperheroDTO>> getSuperheros(@PathVariable @NotBlank String word) {
        log.info("Request received to list superheroes with coincidences in the name with the word: {}", word);
        List<SuperheroDTO> superheroes = superheroService.getSuperheroes(word);
        if (superheroes.isEmpty()) {
            log.info("Request finished with status 404");
            return ResponseEntity.notFound().build();
        }
        log.info("Request finished with status 200");
        return ResponseEntity.ok(superheroes);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuperheroDTO> postSuperhero(@Valid @RequestBody SuperheroDTO superhero) throws URISyntaxException {
        log.info("Request received to insert new superhero: {}", superhero);
        SuperheroDTO superheroInserted = superheroService.insertSuperhero(superhero);
        log.info("Request finished with status 200");
        URI uri = new URI("/superhero/" + superheroInserted.getId());
        return ResponseEntity.created(uri).body(superheroInserted);
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuperheroDTO> putSuperhero(@PathVariable @PositiveOrZero Long id, @Valid @RequestBody SuperheroDTO superhero) {
        log.info("Request received to update superhero with id: {}. New data {}", id, superhero);
        Optional<SuperheroDTO> superheroUpdated = superheroService.updateSuperhero(id, superhero);
        if (superheroUpdated.isEmpty()) {
            log.info("Request finished with status 404");
            return ResponseEntity.notFound().build();
        }
        log.info("Request finished with status 200");
        return ResponseEntity.ok(superheroUpdated.get());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSuperheroById(@PathVariable @PositiveOrZero Long id) {
        log.info("Request received to delete superhero with Id: {}", id);
        Optional<SuperheroDTO> superhero = superheroService.deleteSuperheroById(id);
        if (superhero.isEmpty()) {
            log.info("Request finished with status 404");
            return ResponseEntity.notFound().build();
        }
        log.info("Request finished with status 200");
        return ResponseEntity.ok().build();
    }

}
