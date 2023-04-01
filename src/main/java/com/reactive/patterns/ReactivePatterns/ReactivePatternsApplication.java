package com.reactive.patterns.ReactivePatterns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.reactive.patterns.ReactivePatterns.sec10")
public class ReactivePatternsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactivePatternsApplication.class, args);
	}

}
