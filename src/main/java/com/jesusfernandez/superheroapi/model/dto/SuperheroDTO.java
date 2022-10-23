package com.jesusfernandez.superheroapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class SuperheroDTO {

    @Schema(example = "1651651321", accessMode = Schema.AccessMode.READ_ONLY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Schema(example = "Spiderman", accessMode = Schema.AccessMode.READ_WRITE)
    @NotBlank
    @Size(min = 1, max = 20)
    private String name;

    @Schema(example = "Throw Spider webs", accessMode = Schema.AccessMode.READ_WRITE)
    @NotBlank
    private String power;

    public SuperheroDTO(String name, String power) {
        this.id = null;
        this.name = name;
        this.power = power;
    }

}
