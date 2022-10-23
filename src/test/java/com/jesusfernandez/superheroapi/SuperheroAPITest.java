package com.jesusfernandez.superheroapi;

import com.jesusfernandez.superheroapi.model.dto.SuperheroDTO;
import com.jesusfernandez.superheroapi.service.SuperheroService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SuperheroAPI.class, properties = "spring.cache.type=NONE")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SuperheroAPITest {

    @Autowired
    private SuperheroService superheroService;

    @Test
    @DisplayName("Lists all superheroes")
    public void listSuperheroes() {

        List<SuperheroDTO> superheroes = superheroService.listSuperheroes();

        assertFalse(superheroes.isEmpty(), "Error. Must be not empty");

    }

    @Test
    @DisplayName("Gets superhero by id")
    public void getSuperhero() {

        final Long id = 1L;

        Optional<SuperheroDTO> superhero = superheroService.getSuperhero(id);

        assertFalse(superhero.isEmpty(), "Error. Must be not null");

    }

    @Test
    @DisplayName("Lists all superhero with coincidences in name")
    public void listsCoincidences() {

        final String word = "man";

        List<SuperheroDTO> superheroes = superheroService.getSuperheroes(word);

        assertFalse(superheroes.isEmpty(), "Error. Must be not empty");

    }

    @Test
    @DisplayName("Inserts new superhero")
    public void insertSuperhero() {

        SuperheroDTO superhero = new SuperheroDTO("Doctor Strange", "Strange powers");

        SuperheroDTO insertedSuperhero = superheroService.insertSuperhero(superhero);

        assertNotNull(insertedSuperhero, "Error. Inserted Superhero must not be null");

    }

    @Test
    @DisplayName("Updates superhero")
    public void updateSuperhero() {

        SuperheroDTO superhero = new SuperheroDTO("Batman", "New Superpower");

        Optional<SuperheroDTO> updatedSuperhero = superheroService.updateSuperhero(1L, superhero);

        assertFalse(updatedSuperhero.isEmpty(), "Error. Updated Superhero must not be null");
        assertEquals(superhero, updatedSuperhero.get(), "Error. Both superhero DTOs must be equals");

    }

}
