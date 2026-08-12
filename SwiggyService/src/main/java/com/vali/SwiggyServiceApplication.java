package com.vali;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(
				title = "Swiggy user management api",
				version = "1.0",
				description = "API for swiggy user registration and login"
				))
@SpringBootApplication
@EnableCaching
public class SwiggyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwiggyServiceApplication.class, args);
	}

}
