package com.jesusfernandez.superheroapi.model.dto;

import lombok.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class SuperheroDTO {

    @EqualsAndHashCode.Exclude
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String power;

    public SuperheroDTO(String name, String power) {
        this.id = null;
        this.name = name;
        this.power = power;
    }

}
