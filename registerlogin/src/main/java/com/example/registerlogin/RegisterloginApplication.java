package com.example.registerlogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class RegisterloginApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegisterloginApplication.class, args);
	}

	@GetMapping("/root")
	public String apiRoot() {
		return "../registerlogin/frontend/login.hmtl";
	}

}

