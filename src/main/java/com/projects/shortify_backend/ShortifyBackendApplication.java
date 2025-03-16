package com.projects.shortify_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ShortifyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortifyBackendApplication.class, args);
	}

}
