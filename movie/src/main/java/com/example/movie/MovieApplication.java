package com.example.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // @Scheduling 활성화
@EnableJpaAuditing
@SpringBootApplication
public class MovieApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieApplication.class, args);
	}

}
