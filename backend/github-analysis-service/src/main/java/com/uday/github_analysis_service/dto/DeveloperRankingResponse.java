package com.uday.github_analysis_service.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class DeveloperRankingResponse implements Serializable {

    private int rank;

    private String username;

    private int score;

    private String developerLevel;
}