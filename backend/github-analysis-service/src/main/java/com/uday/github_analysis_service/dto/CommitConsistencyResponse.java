package com.uday.github_analysis_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@Schema(description = "Commit Consistency Analysis Response")
public class CommitConsistencyResponse implements Serializable {

    @Schema(description = "GitHub username", example = "Uday-Saraswat")
    private String username;

    @Schema(description = "Total GitHub events analyzed", example = "250")
    private int totalEvents;

    @Schema(description = "Number of days with at least one commit/activity", example = "45")
    private int activeDays;

    @Schema(description = "Average commits per active day", example = "5.56")
    private double averageCommitsPerDay;

    @Schema(description = "Consistency score calculated from commit activity", example = "88")
    private int consistencyScore;
}