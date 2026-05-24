package com.movie_catalog.movieapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Entry point for the Spring Boot application; @SpringBootApplication enables auto-configuration and component scanning.
@SpringBootApplication
public class MovieappApplication {

	// Boots up the Spring application context and starts the embedded web server.
	public static void main(String[] args) {
		SpringApplication.run(MovieappApplication.class, args);
	}

}
