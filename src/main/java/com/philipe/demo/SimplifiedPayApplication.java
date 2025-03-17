package com.philipe.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EntityScan("com.philipe.demo.domains")
@EnableFeignClients
public class SimplifiedPayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimplifiedPayApplication.class, args);
	}

}
