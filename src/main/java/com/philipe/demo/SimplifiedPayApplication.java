package com.philipe.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.philipe.demo.domains")
public class SimplifiedPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimplifiedPayApplication.class, args);
	}

}
