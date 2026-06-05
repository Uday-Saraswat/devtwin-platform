package com.uday.github_analysis_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@Schema(description = "Developer Ranking Response")
public class DeveloperRankingResponse implements Serializable {

    @Schema(description = "Developer rank position", example = "1")
    private int rank;

    @Schema(description = "GitHub username", example = "Uday-Saraswat")
    private String username;

    @Schema(description = "Developer score calculated from GitHub metrics", example = "95")
    private int score;

    @Schema(description = "Developer level based on score", example = "Expert")
    private String developerLevel;
}