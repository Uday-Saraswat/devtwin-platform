package com.uday.github_analysis_service.dto;

import lombok.Data;

@Data
public class GithubUserResponse {

    private String login;
    private String name;
    private String bio;
    private int public_repos;
    private int followers;
}
