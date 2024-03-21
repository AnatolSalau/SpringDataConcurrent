package com.example.versionannotationhabr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VersionAnnotationHabrApplication {

	public static void main(String[] args) {
		SpringApplication.run(VersionAnnotationHabrApplication.class, args);
	}

}
