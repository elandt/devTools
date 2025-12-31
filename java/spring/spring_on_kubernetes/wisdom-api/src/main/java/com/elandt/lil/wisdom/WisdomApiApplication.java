package com.elandt.lil.wisdom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.micrometer.metrics.autoconfigure.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;

import io.micrometer.core.instrument.MeterRegistry;

@SpringBootApplication
public class WisdomApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WisdomApiApplication.class, args);
	}

	@Bean
	MeterRegistryCustomizer<MeterRegistry> metricsCommonTags(@Value("${spring.application.name}") String appName) {
		// This adds the app name to the tags of all metrics
		return registry -> registry.config().commonTags("app", appName);
	}
}
