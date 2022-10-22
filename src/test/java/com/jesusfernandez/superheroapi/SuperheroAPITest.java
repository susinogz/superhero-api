package com.jesusfernandez.superheroapi;

import com.jesusfernandez.superheroapi.service.SuperheroService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SuperheroAPITest {

    @Autowired
    SuperheroService superheroService;

    @Test
    @DisplayName("Lists all superheroes")
    public void listSuperheroes() {
        //TODO to implement
        superheroService.listSuperheroes();
    }

    @Test
    @DisplayName("Gets superhero by id")
    public void getSuperhero() {
        //TODO to implement
        superheroService.getSuperhero();
    }

    @Test
    @DisplayName("Lists all superhero with coincidences in name")
    public void listsCoincidences() {
        //TODO to implement
    }

    @Test
    @DisplayName("Inserts new superhero")
    public void insertSuperhero() {
        //TODO to implement
        SuperheroDTO superhero = new SuperheroDTO("Batman", "Stealth");
        superheroService.insertSuperhero(superhero);
    }

    @Test
    @DisplayName("Updates superhero")
    public void updateSuperhero() {
        //TODO to implement
    }


}
