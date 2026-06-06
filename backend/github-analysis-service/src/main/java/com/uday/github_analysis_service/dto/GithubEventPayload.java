package com.uday.github_analysis_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "GitHub Event Payload")
public class GithubEventPayload {

    @Schema(description = "List of commits associated with the GitHub event")
    private List<Commit> commits;

    @Data
    @Schema(description = "GitHub Commit Information")
    public static class Commit {

        @Schema(description = "Commit message", example = "feat: add developer ranking API")
        private String message;
    }
}