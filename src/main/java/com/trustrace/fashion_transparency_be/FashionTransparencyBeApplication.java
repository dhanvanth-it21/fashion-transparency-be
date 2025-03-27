package com.trustrace.fashion_transparency_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class FashionTransparencyBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FashionTransparencyBeApplication.class, args);
	}

}
