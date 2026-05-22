package com.uday.github_analysis_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommitConsistencyResponse {

    private String username;

    private int totalEvents;

    private int activeDays;

    private double averageCommitsPerDay;

    private int consistencyScore;

}
