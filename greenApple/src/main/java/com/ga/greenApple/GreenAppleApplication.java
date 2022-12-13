package com.ga.greenApple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.ga.greenApple.controller"})
@SpringBootApplication
public class GreenAppleApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenAppleApplication.class, args);
	}

}
