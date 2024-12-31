package com.elandt.lil.student_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class StudentClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentClientApplication.class, args);
	}

	// Define the WebClient bean
	@Bean
	WebClient webClient(WebClient.Builder builder) {
		// This info could also be supplied via properties, but not necessary for the course
		return builder
				.baseUrl("http://localhost:8080")
				.build();
	}
}
