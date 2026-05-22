package com.uday.github_analysis_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class GithubEventPayload {

    private List<Commit> commits;

    @Data
    public static class Commit {
        private String message;
    }
}