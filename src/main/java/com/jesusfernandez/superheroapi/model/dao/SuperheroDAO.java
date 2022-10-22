package com.jesusfernandez.superheroapi.model.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Superhero")
@Data
public class SuperheroDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable = false, unique = true)
    private String name;

    @Column(name = "Power", nullable = false)
    private String power;

}
