package com.uday.github_analysis_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "GitHub Repository Information")
public class GithubRepoResponse {

    @Schema(description = "Repository name", example = "devtwin-platform")
    private String name;

    @Schema(description = "Primary programming language used in the repository", example = "Java")
    private String language;

    @Schema(description = "Total number of stars received by the repository", example = "25")
    private int stargazers_count;

    @Schema(description = "Total number of forks of the repository", example = "8")
    private int forks_count;
}