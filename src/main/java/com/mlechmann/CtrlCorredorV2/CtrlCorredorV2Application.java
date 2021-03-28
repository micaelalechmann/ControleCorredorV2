package com.mlechmann.CtrlCorredorV2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mlechmann"})
@EntityScan(basePackages = { "com.mlechmann" })
public class CtrlCorredorV2Application {
	public static void main(String[] args) {
		SpringApplication.run(CtrlCorredorV2Application.class, args);
	}

}
