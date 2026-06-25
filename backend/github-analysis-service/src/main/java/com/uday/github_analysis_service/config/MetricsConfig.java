package com.uday.github_analysis_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Configuration
public class MetricsConfig {

    @Bean
    public Counter resumeGeneratedCounter(MeterRegistry meterRegistry) {

        return Counter.builder("resume_generated_total")
                .description("Total resumes generated")
                .register(meterRegistry);
    }

    @Bean
    public Counter githubApiCallsCounter(MeterRegistry meterRegistry) {

        return Counter.builder("github_api_calls_total")
                .description("Total GitHub API calls")
                .register(meterRegistry);
    }
}
