package ru.alov.market.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("mail.properties")
public class AuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}
}
