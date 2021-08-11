package com.jefweee.eideticspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;

import java.util.Map;

@SpringBootApplication(exclude = EmbeddedMongoAutoConfiguration.class)
public class EideticSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(EideticSpringApplication.class, args);
	}

}
