package com.jesusfernandez.superheroapi;

import static com.jesusfernandez.superheroapi.utils.JsonParser.json;

import com.jesusfernandez.superheroapi.model.dto.SuperheroDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SuperheroAPI.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SuperheroAPIRestTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @PostConstruct
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @DisplayName("Gets all Superheroes in database")
    public void listSuperheroes() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/superhero/list")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(
                        MockMvcResultHandlers.print()
                )
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$").isArray()
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$").isNotEmpty()
                );
    }

    @Test
    @DisplayName("Gets information of Superhero from database by Id")
    public void getSuperhero() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/superhero/{id}", 1)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(
                        MockMvcResultHandlers.print()
                )
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").value(1)
                );
    }

    @Test
    @DisplayName("Try to get information of non exists Superhero in database")
    public void notFoundSuperhero() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/superhero/{id}", Integer.MAX_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(
                        MockMvcResultHandlers.print()
                )
                .andExpect(
                        MockMvcResultMatchers.status().isNotFound()
                );
    }

    @Test
    @DisplayName("Try to get information of Superhero in database with negative id")
    public void badIdGetSuperhero() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/superhero/{id}", -1)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(
                        MockMvcResultHandlers.print()
                )
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                );
    }

    @Test
    @DisplayName("Gets Superheroes information from database for any matching word")
    public void getSuperheroCoincidences() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/superhero/list/{word}", "man")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(
                        MockMvcResultHandlers.print()
                )
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$").isArray()
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$").isNotEmpty()
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.[0].name").value(Matchers.containsString("man"))
                );
    }

    @Test
    @DisplayName("Try to get superheroes from database for any match when param is blank")
    public void blankWordGetSuperheroCoincidences() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/superhero/list/{word}", "")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(
                        MockMvcResultHandlers.print()
                )
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                );
    }

    @Test
    @DisplayName("Save Superhero information into database")
    public void insertSuperhero() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/superhero")
                                .content(
                                        json(
                                                new SuperheroDTO("Captain Marvel", "Strong")
                                        )
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(
                        MockMvcResultHandlers.print()
                )
                .andExpect(
                        MockMvcResultMatchers.status().isCreated()
                );
    }

    @Test
    @DisplayName("Try to save a new superhero with null name in database")
    public void nullNameInsertingSuperhero() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/superhero")
                                .content(
                                        json(
                                                new SuperheroDTO(null, "Strong")
                                        )
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(
                        MockMvcResultHandlers.print()
                )
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                );
    }

    @Test
    @DisplayName("Try to save a new superhero with blank name in database")
    public void blankNameInsertingSuperhero() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/superhero")
                                .content(
                                        json(
                                                new SuperheroDTO("", "Strong")
                                        )
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(
                        MockMvcResultHandlers.print()
                )
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                );
    }

    @Test
    @DisplayName("Try to save a new Superhero with existing name in database")
    public void badNameInsertingSuperhero() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/superhero")
                                .content(
                                        json(
                                                new SuperheroDTO("Batman", "Strong")
                                        )
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(
                        MockMvcResultHandlers.print()
                )
                .andExpect(
                        MockMvcResultMatchers.status().isConflict()
                );
    }

    @Test
    @DisplayName("Updates Superhero information in database")
    public void updateSuperhero() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/superhero/{id}", 1)
                        .content(
                                json(new SuperheroDTO("IronMan", "Iron"))
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("IronMan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.power").value("Iron"));
    }

    @Test
    @DisplayName("Try to update superhero information with existing name in database")
    public void badNameUpdatingSuperhero() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/superhero/{id}", 1)
                        .content(
                                json(new SuperheroDTO("Batman", "Strong"))
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.status().isConflict()
                );
    }

    @Test
    @DisplayName("Try to update superhero information with null name")
    public void nullNameUpdatingSuperhero() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/superhero/{id}", 1)
                        .content(
                                json(new SuperheroDTO(null, "Strong"))
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                );
    }

    @Test
    @DisplayName("Try to update superhero information with blank name")
    public void blankNameUpdatingSuperhero() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/superhero/{id}", 1)
                        .content(
                                json(new SuperheroDTO("", "Strong"))
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                );
    }

    @Test
    @DisplayName("Try to update superhero information with negative id")
    public void badIdUpdatingSuperhero() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/superhero/{id}", -1)
                        .content(
                                json(new SuperheroDTO("Batman", "Strong"))
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                );
    }

    @Test
    @DisplayName("Delete Superhero information by Id from database")
    public void deleteSuperhero() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/superhero/{id}", 1)
                )
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    @DisplayName("Try to delete non-existent superhero in database")
    public void notFoundDeletingSuperhero() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/superhero/{id}", Integer.MAX_VALUE)
                )
                .andExpect(
                        MockMvcResultMatchers.status().isNotFound()
                );
    }

    @Test
    @DisplayName("Try to use negative id to delete superhero in database")
    public void badIdDeletingSuperhero() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/superhero/{id}", Integer.MAX_VALUE)
                )
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                );
    }

}
