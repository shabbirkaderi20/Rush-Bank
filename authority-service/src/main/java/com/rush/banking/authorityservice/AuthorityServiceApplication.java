package com.rush.banking.authorityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthorityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorityServiceApplication.class, args);
	}

}
