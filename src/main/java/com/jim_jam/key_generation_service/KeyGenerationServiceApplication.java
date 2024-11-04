package com.jim_jam.key_generation_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class KeyGenerationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeyGenerationServiceApplication.class, args);
	}

}
