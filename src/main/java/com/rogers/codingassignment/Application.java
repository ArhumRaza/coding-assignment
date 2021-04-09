package com.rogers.codingassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}

	@GetMapping
	public List<String> hello() {
		List<String> json = new ArrayList<>();
		json.add("Hello");
		json.add("World");
		return json;
	}

}
