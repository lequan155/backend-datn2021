package com.datn2021;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:3000")
public class DatnBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatnBackendApplication.class, args);
	}

}
