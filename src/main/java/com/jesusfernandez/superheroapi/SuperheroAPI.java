package com.jesusfernandez.superheroapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.jesusfernandez.superheroapi.repository")
public class SuperheroAPI {

	public static void main(String[] args) {
		SpringApplication.run(SuperheroAPI.class, args);
	}

}
