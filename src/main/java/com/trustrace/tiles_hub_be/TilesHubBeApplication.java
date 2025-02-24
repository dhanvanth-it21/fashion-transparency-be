package com.trustrace.tiles_hub_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class TilesHubBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TilesHubBeApplication.class, args);
	}

}
