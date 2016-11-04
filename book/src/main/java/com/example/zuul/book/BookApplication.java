package com.example.zuul.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class BookApplication {


	@RequestMapping(value = "/available")
	public String available() {
		return "Spring in Action";
	}


	@RequestMapping(value = "/checked-out")
	public String checkedOut() {
		return "Spring Boot in Action";
	}


	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);
	}
}
