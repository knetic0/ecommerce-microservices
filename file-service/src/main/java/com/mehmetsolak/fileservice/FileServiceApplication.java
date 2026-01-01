package com.mehmetsolak.fileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(
		scanBasePackages = {
				"com.mehmetsolak.fileservice",
				"com.mehmetsolak.advice"
		}
)
@EnableDiscoveryClient
public class FileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileServiceApplication.class, args);
	}

}
