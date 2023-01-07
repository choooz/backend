package com.example.manymanyUsers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ManymanyUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManymanyUsersApplication.class, args);
	}

}
