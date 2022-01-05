package com.elandt.lil.sbet.fizzbuzz.fizzbuzz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FizzbuzzApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(FizzbuzzApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FizzbuzzApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return args -> {
			for (int i = 1; i < 101; i++) {
				boolean divBy3 = i % 3 == 0;
				boolean divBy5 = i % 5 == 0;
				if(divBy3 && divBy5){
					LOGGER.info("FizzBuzz");
				} else if(divBy3) {
					LOGGER.info("Fizz");
				} else if(divBy5) {
					LOGGER.info("Buzz");
				} else {
					LOGGER.info(String.valueOf(i));
				}				
			}
		};
	}
}
