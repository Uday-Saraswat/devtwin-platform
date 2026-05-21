package com.uday.github_analysis_service.dto;

import lombok.Data;

@Data
public class GithubEventResponse {

    private String type;

    private String created_at;
}
