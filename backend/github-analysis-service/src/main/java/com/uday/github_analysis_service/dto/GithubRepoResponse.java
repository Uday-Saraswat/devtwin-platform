package com.uday.github_analysis_service.dto;

import lombok.Data;

@Data
public class GithubRepoResponse {

    private String name;

    private String language;

    private int stargazers_count;

    private int forks_count;
}
