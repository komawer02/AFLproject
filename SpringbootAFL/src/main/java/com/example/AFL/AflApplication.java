package com.example.AFL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AflApplication {

	public static void main(String[] args) {
		SpringApplication.run(AflApplication.class, args);
	}

}
