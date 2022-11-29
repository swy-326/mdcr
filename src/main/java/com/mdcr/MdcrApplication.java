package com.mdcr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MdcrApplication {

	public static void main(String[] args) {
		SpringApplication.run(MdcrApplication.class, args);
	}

}