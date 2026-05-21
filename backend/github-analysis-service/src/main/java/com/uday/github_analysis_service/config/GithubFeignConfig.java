package com.uday.github_analysis_service.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GithubFeignConfig {

    @Value("${github.token}")
    private String githubToken;

    @Bean
    public RequestInterceptor requestInterceptor() {

        return requestTemplate -> {
            requestTemplate.header("Authorization", "Bearer " + githubToken);

            requestTemplate.header(
                    "Accept",
                    "application/vnd.github+json"
            );

        };
    }

}
