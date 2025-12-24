package com.linkedin.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import lombok.extern.slf4j.Slf4j;

@EnableAsync
@SpringBootApplication
@Slf4j
public class EventsApplication
{
	public static void main(String[] args) {
		SpringApplication.run(EventsApplication.class, args);
	}

}