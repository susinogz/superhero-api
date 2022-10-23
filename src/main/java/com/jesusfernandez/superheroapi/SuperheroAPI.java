package com.jesusfernandez.superheroapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@EnableJpaRepositories("com.jesusfernandez.superheroapi.repository")
@EnableTransactionManagement
@SpringBootApplication
public class SuperheroAPI {

    public static void main(String[] args) {
        SpringApplication.run(SuperheroAPI.class, args);
    }

}