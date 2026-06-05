package com.uday.github_analysis_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "GitHub Event Response")
public class GithubEventResponse {

    @Schema(description = "Type of GitHub event", example = "PushEvent")
    private String type;

    @Schema(description = "Timestamp when the event was created", example = "2026-06-05T10:15:30Z")
    private String created_at;

    @Schema(description = "Additional event details including commit information")
    private GithubEventPayload payload;
}