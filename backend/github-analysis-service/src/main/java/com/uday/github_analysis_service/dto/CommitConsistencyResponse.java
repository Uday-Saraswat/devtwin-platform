package com.uday.github_analysis_service.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CommitConsistencyResponse implements Serializable {

    private String username;

    private int totalEvents;

    private int activeDays;

    private double averageCommitsPerDay;

    private int consistencyScore;

}
