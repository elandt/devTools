package com.elandt.lil.ec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Explore California API",
		description = "API Definitions of the Explore California Microservice",
		version = "3.0.1"
	)
)
public class ExplorecaliApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExplorecaliApplication.class, args);
	}
}
