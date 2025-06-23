package com.ruang.hh99;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HH99Application {

	public static void main(String[] args) {

		SpringApplication.run(HH99Application.class, args);

		System.out.println("===================================================");
		System.out.println("서버 : http://localhost:8080");
		System.out.println("swagger UI : http://localhost:8080/swagger-ui.html");
		System.out.println("===================================================");

	}

}
