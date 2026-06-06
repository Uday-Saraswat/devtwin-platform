package com.uday.github_analysis_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "GitHub User Profile Information")
public class GithubUserResponse {

    @Schema(description = "GitHub username", example = "Uday-Saraswat")
    private String login;

    @Schema(description = "Developer full name", example = "Uday Saraswat")
    private String name;

    @Schema(description = "Developer biography from GitHub profile",
            example = "Java Full Stack Developer | Spring Boot | Microservices")
    private String bio;

    @Schema(description = "Total public repositories", example = "27")
    private int public_repos;

    @Schema(description = "Total GitHub followers", example = "145")
    private int followers;
}