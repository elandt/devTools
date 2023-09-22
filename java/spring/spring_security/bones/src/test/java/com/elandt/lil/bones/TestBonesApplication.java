package com.elandt.lil.bones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestBonesApplication {

	public static void main(String[] args) {
		SpringApplication.from(BonesApplication::main).with(TestBonesApplication.class).run(args);
	}

}
