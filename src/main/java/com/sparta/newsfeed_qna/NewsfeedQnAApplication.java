package com.sparta.newsfeed_qna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class NewsfeedQnAApplication {
	public static void main(String[] args) {
		SpringApplication.run(NewsfeedQnAApplication.class, args);
	}
}
