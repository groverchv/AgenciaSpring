package com.AgenciaSpring.AgenciaSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AgenciaSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgenciaSpringApplication.class, args);
	}

}
