package com.jesusfernandez.superheroapi.controller;

import com.jesusfernandez.superheroapi.model.dto.SuperheroDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(
        path = "superhero",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Slf4j
public class Controller {

    @GetMapping(path = "list")
    public ResponseEntity<List<SuperheroDTO>> listSuperheroes() {
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<SuperheroDTO> getSuperhero(@PathVariable @PositiveOrZero Long id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "list/{word}")
    public ResponseEntity<List<SuperheroDTO>> getSuperheros(@PathVariable @NotBlank String word) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<SuperheroDTO> postSuperhero(@Valid @RequestBody SuperheroDTO superhero) throws URISyntaxException {
        return ResponseEntity.created(new URI("")).build();
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<SuperheroDTO> putSuperhero(@Valid @RequestBody SuperheroDTO superhero) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSuperheroById(@PathVariable @PositiveOrZero Long id) {
        return ResponseEntity.ok().build();
    }

}
