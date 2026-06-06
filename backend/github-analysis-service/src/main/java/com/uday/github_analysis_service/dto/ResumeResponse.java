package com.uday.github_analysis_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@Schema(description = "AI Generated Developer Resume")
public class ResumeResponse implements Serializable {

    @Schema(description = "Developer full name", example = "Uday Saraswat")
    private String name;

    @Schema(description = "GitHub username", example = "Uday-Saraswat")
    private String githubUsername;

    @Schema(description = "Developer bio from GitHub profile",
            example = "Java Full Stack Developer | Spring Boot | Microservices")
    private String bio;

    @Schema(description = "Total number of public repositories", example = "25")
    private int publicRepos;

    @Schema(description = "Total GitHub followers", example = "150")
    private int followers;

    @Schema(description = "Top programming languages used by the developer",
            example = "[\"Java\", \"JavaScript\", \"Python\"]")
    private List<String> topLanguages;

    @Schema(description = "Most relevant repositories",
            example = "[\"devtwin-platform\", \"ecommerce-microservices\", \"spring-security-jwt\"]")
    private List<String> topRepositories;

    @Schema(description = "Developer expertise level", example = "Advanced")
    private String developerLevel;

    @Schema(description = "Developer score calculated from GitHub metrics", example = "92")
    private int developerScore;
}