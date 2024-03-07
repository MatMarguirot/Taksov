package com.mat.taksov;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration
public class TaksovApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaksovApplication.class, args);
	}

}

//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class}) //doesnt work either


