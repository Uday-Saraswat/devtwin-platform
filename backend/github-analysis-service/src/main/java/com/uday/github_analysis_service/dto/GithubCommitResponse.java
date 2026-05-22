package com.uday.github_analysis_service.dto;

import lombok.Data;

@Data
public class GithubCommitResponse {

    private Commit commit;

    @Data
    public static class Commit {

        private Author author;
    }

    @Data
    public static class Author {

        private String date;
    }
}
