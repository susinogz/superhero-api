package com.jesusfernandez.superheroapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

	@Value("${project.version}")
	private String projectVersion;

	@Value("${spring-doc.version}")
	private String springDocVersion;

	@Bean
	public OpenAPI api() {
		return new OpenAPI().openapi(springDocVersion).info(info());
	}

	protected Info info() {
		return new Info()
				.title("Superhero API")
				.description("Api to manage Superheroes information in database")
				.version(projectVersion);
	}

}
