package com.jesusfernandez.superheroapi.controller;

import com.jesusfernandez.superheroapi.annotation.interfaces.Timed;
import com.jesusfernandez.superheroapi.model.dto.SuperheroDTO;
import com.jesusfernandez.superheroapi.service.SuperheroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Gets all Superheroes in database",
            description = "Gets all Superheroes in database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok. Superheroes information found",
                            content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request. Missing data or json wrong formed",
                            content = @Content(schema = @Schema(example = "{\n" +
                                    "  \"timestamp\": \"2022-10-19T14:41:55.205+0000\",\n" +
                                    "  \"status\": 400,\n" +
                                    "  \"error\": \"Bad Request\",\n" +
                                    "  \"message\": \"Bad Request. Missing data or json wrong formed\",\n" +
                                    "  \"path\": \"/v1/superhero-api\"\n" +
                                    "}"))),
                    @ApiResponse(responseCode = "404", description = "Not found. Not found data in database",
                            content = @Content(schema = @Schema(example = "{\n" +
                                    "  \"timestamp\": \"2022-10-19T14:41:55.205+0000\",\n" +
                                    "  \"status\": 404,\n" +
                                    "  \"error\": \"Not Found\",\n" +
                                    "  \"message\": \"Not Found. Not found data in database\",\n" +
                                    "  \"path\": \"/v1/superhero-api\"\n" +
                                    "}"))),
                    @ApiResponse(responseCode = "500", description = "Internal error. An internal error has occurred on the server",
                            content = @Content(schema = @Schema(example = "{\n" +
                                    "  \"timestamp\": \"2019-02-20T14:41:55.205+0000\",\n" +
                                    "  \"status\": 500,\n" +
                                    "  \"error\": \"Internal Error\",\n" +
                                    "  \"message\": \"Internal Error\",\n" +
                                    "  \"path\": \"/v1/superhero-api\"\n" +
                                    "}")))
            })
    @Timed
    @GetMapping(path = "list")
    public ResponseEntity<List<SuperheroDTO>> listSuperheroes() {
        log.info("Request received to list all superheroes");
        List<SuperheroDTO> superheroes = superheroService.listSuperheroes();
        log.info("Request finished with status 200");
        return ResponseEntity.ok(superheroes);
    }

    @Operation(summary = "Gets information of Superhero from database by Id",
            description = "Gets information of Superhero from database by Id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok. Superhero information found",
                            content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request. Missing data or json wrong formed",
                            content = @Content(schema = @Schema(example = "{\n" +
                                    "  \"timestamp\": \"2022-10-19T14:41:55.205+0000\",\n" +
                                    "  \"status\": 400,\n" +
                                    "  \"error\": \"Bad Request\",\n" +
                                    "  \"message\": \"Bad Request. Missing data or json wrong formed\",\n" +
                                    "  \"path\": \"/v1/superhero-api\"\n" +
                                    "}"))),
                    @ApiResponse(responseCode = "404", description = "Not found. Not found data in database",
                            content = @Content(schema = @Schema(example = "{\n" +
                                    "  \"timestamp\": \"2022-10-19T14:41:55.205+0000\",\n" +
                                    "  \"status\": 404,\n" +
                                    "  \"error\": \"Not Found\",\n" +
                                    "  \"message\": \"Not Found. Not found data in database\",\n" +
                                    "  \"path\": \"/v1/superhero-api\"\n" +
                                    "}"))),
                    @ApiResponse(responseCode = "500", description = "Internal error. An internal error has occurred on the server",
                            content = @Content(schema = @Schema(example = "{\n" +
                                    "  \"timestamp\": \"2019-02-20T14:41:55.205+0000\",\n" +
                                    "  \"status\": 500,\n" +
                                    "  \"error\": \"Internal Error\",\n" +
                                    "  \"message\": \"Internal Error\",\n" +
                                    "  \"path\": \"/v1/superhero-api\"\n" +
                                    "}")))
            })
    @Timed
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

    @Operation(summary = "Gets Superheroes information from database for any matching word",
            description = "Gets Superheroes information from database for any matching word",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok. Superhero information found",
                            content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request. Missing data or json wrong formed",
                            content = @Content(schema = @Schema(example = "{\n" +
                                    "  \"timestamp\": \"2022-10-19T14:41:55.205+0000\",\n" +
                                    "  \"status\": 400,\n" +
                                    "  \"error\": \"Bad Request\",\n" +
                                    "  \"message\": \"Bad Request. Missing data or json wrong formed\",\n" +
                                    "  \"path\": \"/v1/superhero-api\"\n" +
                                    "}"))),
                    @ApiResponse(responseCode = "404", description = "Not found. Not found data in database",
                            content = @Content(schema = @Schema(example = "{\n" +
                                    "  \"timestamp\": \"2022-10-19T14:41:55.205+0000\",\n" +
                                    "  \"status\": 404,\n" +
                                    "  \"error\": \"Not Found\",\n" +
                                    "  \"message\": \"Not Found. Not found data in database\",\n" +
                                    "  \"path\": \"/v1/superhero-api\"\n" +
                                    "}"))),
                    @ApiResponse(responseCode = "500", description = "Internal error. An internal error has occurred on the server",
                            content = @Content(schema = @Schema(example = "{\n" +
                                    "  \"timestamp\": \"2019-02-20T14:41:55.205+0000\",\n" +
                                    "  \"status\": 500,\n" +
                                    "  \"error\": \"Internal Error\",\n" +
                                    "  \"message\": \"Internal Error\",\n" +
                                    "  \"path\": \"/v1/superhero-api\"\n" +
                                    "}")))
            })
    @Timed
    @GetMapping(path = "coincidences/{word}")
    public ResponseEntity<List<SuperheroDTO>> getSuperheros(@PathVariable @NotBlank String word) {
        log.info("Request received to list superheroes with coincidences in the name with the word: {}", word);
        List<SuperheroDTO> superheroes = superheroService.getSuperheroes(word);
        log.info("Request finished with status 200");
        return ResponseEntity.ok(superheroes);
    }

    @Operation(summary = "Save Superhero information into database",
            description = "Save Superhero information into database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok. Superhero information saved successfully",
                            content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request. Missing data or json wrong formed",
                            content = @Content(schema = @Schema(example = "{\n" +
                                    "  \"timestamp\": \"2022-10-19T14:41:55.205+0000\",\n" +
                                    "  \"status\": 400,\n" +
                                    "  \"error\": \"Bad Request\",\n" +
                                    "  \"message\": \"Bad Request. Missing data or json wrong formed\",\n" +
                                    "  \"path\": \"/v1/superhero-api\"\n" +
                                    "}"))),
                    @ApiResponse(responseCode = "404", description = "Not found. Not found data in database",
                            content = @Content(schema = @Schema(example = "{\n" +
                                    "  \"timestamp\": \"2022-10-19T14:41:55.205+0000\",\n" +
                                    "  \"status\": 404,\n" +
                                    "  \"error\": \"Not Found\",\n" +
                                    "  \"message\": \"Not Found. Not found data in database\",\n" +
                                    "  \"path\": \"/v1/superhero-api\"\n" +
                                    "}"))),
                    @ApiResponse(responseCode = "409", description = "Conflict. No data inserted",
                            content = @Content(schema = @Schema(example = "{\n" +
                                    "  \"timestamp\": \"2022-10-19T14:41:55.205+0000\",\n" +
                                    "  \"status\": 409,\n" +
                                    "  \"error\": \"Conflict\",\n" +
                                    "  \"message\": \"Conflict. No data inserted\",\n" +
                                    "  \"path\": \"/v1/superhero-api\"\n" +
                                    "}"))),
                    @ApiResponse(responseCode = "500", description = "Internal error. An internal error has occurred on the server",
                            content = @Content(schema = @Schema(example = "{\n" +
                                    "  \"timestamp\": \"2019-02-20T14:41:55.205+0000\",\n" +
                                    "  \"status\": 500,\n" +
                                    "  \"error\": \"Internal Error\",\n" +
                                    "  \"message\": \"Internal Error\",\n" +
                                    "  \"path\": \"/v1/superhero-api\"\n" +
                                    "}")))
            })
    @Timed
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuperheroDTO> postSuperhero(@Valid @RequestBody SuperheroDTO superhero) throws URISyntaxException {
        log.info("Request received to insert new superhero: {}", superhero);
        SuperheroDTO superheroInserted = superheroService.insertSuperhero(superhero);
        log.info("Request finished with status 200");
        URI uri = new URI("/superhero/" + superheroInserted.getId());
        return ResponseEntity.created(uri).body(superheroInserted);
    }

    @Operation(summary = "Updates Superhero information in database",
            description = "Updates Superhero information in database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok. Superhero information updated",
                            content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request. Missing data or json wrong formed",
                            content = @Content(schema = @Schema(example = "{\n" +
                                    "  \"timestamp\": \"2022-10-19T14:41:55.205+0000\",\n" +
                                    "  \"status\": 400,\n" +
                                    "  \"error\": \"Bad Request\",\n" +
                                    "  \"message\": \"Bad Request. Missing data or json wrong formed\",\n" +
                                    "  \"path\": \"/v1/superhero-api\"\n" +
                                    "}"))),
                    @ApiResponse(responseCode = "404", description = "Not found. Not found data in database",
                            content = @Content(schema = @Schema(example = "{\n" +
                                    "  \"timestamp\": \"2022-10-19T14:41:55.205+0000\",\n" +
                                    "  \"status\": 404,\n" +
                                    "  \"error\": \"Not Found\",\n" +
                                    "  \"message\": \"Not Found. Not found data in database\",\n" +
                                    "  \"path\": \"/v1/superhero-api\"\n" +
                                    "}"))),
                    @ApiResponse(responseCode = "500", description = "Internal error. An internal error has occurred on the server",
                            content = @Content(schema = @Schema(example = "{\n" +
                                    "  \"timestamp\": \"2019-02-20T14:41:55.205+0000\",\n" +
                                    "  \"status\": 500,\n" +
                                    "  \"error\": \"Internal Error\",\n" +
                                    "  \"message\": \"Internal Error\",\n" +
                                    "  \"path\": \"/v1/superhero-api\"\n" +
                                    "}")))
            })
    @Timed
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

    @Operation(summary = "Delete Superhero information by Id from database",
            description = "Delete Superhero information by Id from database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok. Superhero information deleted",
                            content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request. Missing data or json wrong formed",
                            content = @Content(schema = @Schema(example = "{\n" +
                                    "  \"timestamp\": \"2022-10-19T14:41:55.205+0000\",\n" +
                                    "  \"status\": 400,\n" +
                                    "  \"error\": \"Bad Request\",\n" +
                                    "  \"message\": \"Bad Request. Missing data or json wrong formed\",\n" +
                                    "  \"path\": \"/v1/superhero-api\"\n" +
                                    "}"))),
                    @ApiResponse(responseCode = "404", description = "Not found. Not found data in database",
                            content = @Content(schema = @Schema(example = "{\n" +
                                    "  \"timestamp\": \"2022-10-19T14:41:55.205+0000\",\n" +
                                    "  \"status\": 404,\n" +
                                    "  \"error\": \"Not Found\",\n" +
                                    "  \"message\": \"Not Found. Not found data in database\",\n" +
                                    "  \"path\": \"/v1/superhero-api\"\n" +
                                    "}"))),
                    @ApiResponse(responseCode = "500", description = "Internal error. An internal error has occurred on the server",
                            content = @Content(schema = @Schema(example = "{\n" +
                                    "  \"timestamp\": \"2019-02-20T14:41:55.205+0000\",\n" +
                                    "  \"status\": 500,\n" +
                                    "  \"error\": \"Internal Error\",\n" +
                                    "  \"message\": \"Internal Error\",\n" +
                                    "  \"path\": \"/v1/superhero-api\"\n" +
                                    "}")))
            })
    @Timed
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
