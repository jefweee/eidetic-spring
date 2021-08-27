package com.jefweee.eideticspring;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Log4j2
@SpringBootApplication(exclude = EmbeddedMongoAutoConfiguration.class)
public class EideticSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(EideticSpringApplication.class, args);
	}
}
