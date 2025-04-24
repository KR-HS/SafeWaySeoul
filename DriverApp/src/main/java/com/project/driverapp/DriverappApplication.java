package com.project.driverapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DriverappApplication {
	public static void main(String[] args) {
		SpringApplication.run(DriverappApplication.class, args);
	}
}
