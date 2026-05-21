package com.uday.github_analysis_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResumeResponse {

    private String name;

    private String githubUsername;

    private String bio;

    private int publicRepos;

    private int followers;

    private List<String> topLanguages;

    private List<String> topRepositories;

    private String developerLevel;

    private int developerScore;
}