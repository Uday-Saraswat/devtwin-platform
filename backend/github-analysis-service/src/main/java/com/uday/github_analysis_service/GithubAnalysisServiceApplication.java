package com.uday.github_analysis_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GithubAnalysisServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GithubAnalysisServiceApplication.class, args);
	}

}
