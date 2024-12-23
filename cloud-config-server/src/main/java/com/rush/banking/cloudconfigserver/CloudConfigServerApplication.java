package com.rush.banking.cloudconfigserver;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudConfigServerApplication {

	public static void main(String[] args) {

		SpringApplication.run(CloudConfigServerApplication.class, args);
	}

}
