package com.besmartexim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "UserSearchManagement API", version = "1.0", description = "User Search Related APIs"))
@SpringBootApplication
public class BesmarteximSearchManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BesmarteximSearchManagementApplication.class, args);
	}

}
