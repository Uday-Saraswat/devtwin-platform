package com.uday.github_analysis_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "GitHub Commit Response")
public class GithubCommitResponse {

    @Schema(description = "Commit information")
    private Commit commit;

    @Data
    @Schema(description = "Commit Details")
    public static class Commit {

        @Schema(description = "Author information")
        private Author author;
    }

    @Data
    @Schema(description = "Commit Author Details")
    public static class Author {

        @Schema(description = "Commit creation date and time", example = "2026-06-05T10:15:30Z")
        private String date;
    }
}