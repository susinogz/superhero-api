package com.jesusfernandez.superheroapi;

import com.jesusfernandez.superheroapi.model.dao.SuperheroDAO;
import com.jesusfernandez.superheroapi.model.dto.SuperheroDTO;
import com.jesusfernandez.superheroapi.repository.SuperheroRepository;
import com.jesusfernandez.superheroapi.service.SuperheroService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.annotation.DirtiesContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Objects;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SuperheroAPICacheTest {

    private static final SuperheroDTO CAPTAIN_AMERICA;
    private static final SuperheroDTO THOR;
    private AutoCloseable autoCloseable;

    @SpyBean
    private SuperheroRepository superheroRepository;

    @Autowired
    @InjectMocks
    private SuperheroService superheroService;

    @Autowired
    private CacheManager cacheManager;

    static {
        CAPTAIN_AMERICA = new SuperheroDTO("Captain America", "Strong");
        THOR = new SuperheroDTO("Thor", "Hammer");
    }

    @PostConstruct
    void postConstruct() {

        autoCloseable = MockitoAnnotations.openMocks(this);

    }

    @BeforeEach
    void beforeEach() {
        cacheManager.getCacheNames().forEach(cacheName -> {
            Objects.requireNonNull(cacheManager.getCache(cacheName)).clear();
        });
    }

    @PreDestroy
    void preDestroy() throws Exception {
        autoCloseable.close();
    }

    @Test
    @DisplayName("Check cache for gets all superheroes in database request")
    void cacheListSuperheroes() {

        superheroService.listSuperheroes();
        superheroService.listSuperheroes();

        Mockito.verify(superheroRepository, Mockito.times(1)).findAll();

    }

    @Test
    @DisplayName("Check cache for gets information of superhero from database by id")
    void cacheGetSuperhero() {

        superheroService.getSuperhero(1L);
        superheroService.getSuperhero(1L);

        Mockito.verify(superheroRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Check the cache by getting superhero info from the database for any matching words")
    void cacheGetSuperheroesCoincidences() {

        final String word = "man";

        superheroService.getSuperheroes(word);
        superheroService.getSuperheroes(word);

        Mockito.verify(superheroRepository, Mockito.times(1)).findCoincidencesInName(word);

    }

    @Test
    @DisplayName("Check the cache on insert superhero method")
    void cacheInsertSuperhero() {

        superheroService.insertSuperhero(CAPTAIN_AMERICA);
        superheroService.insertSuperhero(THOR);

        Mockito.verify(superheroRepository, Mockito.times(2)).save(Mockito.any(SuperheroDAO.class));

    }

    @Test
    @DisplayName("Check the cache on update superhero method")
    void cacheUpdateSuperhero() {

        SuperheroDTO wanda = new SuperheroDTO("Wanda", "Witchcraft");
        SuperheroDTO hulk = new SuperheroDTO("Hulk", "Strong");

        superheroService.updateSuperhero(1L, wanda);
        superheroService.updateSuperhero(2L, hulk);

        Mockito.verify(superheroRepository, Mockito.times(2)).save(Mockito.any(SuperheroDAO.class));

    }

    @Test
    @DisplayName("Check the cache on delete superhero method")
    void cacheDeleteSuperhero() {

        superheroService.deleteSuperheroById(1L);
        superheroService.deleteSuperheroById(2L);

        Mockito.verify(superheroRepository, Mockito.times(2)).deleteById(Mockito.any(Long.class));

    }

}
