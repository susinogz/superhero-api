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
                )
        ;
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
    @DisplayName("Delete Superhero information by Id from database")
    public void deleteSuperhero() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/superhero/{id}", 1)
                )
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                );
    }

}
