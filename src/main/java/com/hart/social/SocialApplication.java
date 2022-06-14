package com.hart.social;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SocialApplication {

	private static final Logger log = LoggerFactory.getLogger(SocialApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SocialApplication.class, args);
		log.info("Social API demo");
	}

}
