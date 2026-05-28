package com.uday.github_analysis_service.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class ResumeResponse implements Serializable {

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