package com.uday.github_analysis_service.controller;


import com.uday.github_analysis_service.dto.CommitConsistencyResponse;
import com.uday.github_analysis_service.dto.DeveloperRankingResponse;
import com.uday.github_analysis_service.service.GithubAnalysisService;
import com.uday.github_analysis_service.service.ResumeCacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/github")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "GitHub Analysis APIs", description = "GitHub Profile Analysis, Resume Generation and Developer Ranking APIs")
public class GithubAnalysisController {

    private final GithubAnalysisService githubAnalysisService;

    private final ResumeCacheService resumeCacheService;

    @Operation(summary = "Analyze GitHub Profile",
            description = "Fetches GitHub profile details, repositories and developer insights.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Profile analyzed successfully"),
            @ApiResponse(responseCode = "404", description = "GitHub user not found")})
    @GetMapping("/analyze/{username}")
    public ResponseEntity<?> analyzeGithubProfile(@PathVariable String username) {

        return ResponseEntity.ok(githubAnalysisService.analyzeProfile(username));
    }

    @Operation(summary = "Generate Resume",
            description = "Generates AI-powered developer resume from GitHub profile.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Resume generated successfully"),
            @ApiResponse(responseCode = "404", description = "GitHub user not found")})
    @GetMapping("/resume/{username}")
    public ResponseEntity<?> generateResume(@PathVariable String username) {
        return ResponseEntity.ok(resumeCacheService.generateResume(username));
    }


    @Operation(summary = "Commit Consistency Analysis",
            description = "Calculates developer commit consistency score.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Analysis completed successfully")})
    @GetMapping("/analytics/commit-consistency/{username}")
    public ResponseEntity<CommitConsistencyResponse> getCommitConsistency(@PathVariable String username) {
        return ResponseEntity.ok(githubAnalysisService.getCommitConsistency(username));
    }

    @Operation(summary = "Generate Resume PDF",
            description = "Downloads generated developer resume as PDF.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "PDF generated successfully")})
    @GetMapping("/resume/pdf/{username}")
    public ResponseEntity<byte[]> generateResumePdf(@PathVariable String username) {
        byte[] pdf = githubAnalysisService.generateResumePdf(username);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=resume.pdf")
                .contentType(MediaType.APPLICATION_PDF).body(pdf);
    }


    @Operation(summary = "Rank Developers",
            description = "Ranks developers based on GitHub activity and profile metrics.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Ranking generated successfully")})
    @PostMapping("/rank")
    public ResponseEntity<List<DeveloperRankingResponse>> rankDevelopers(@RequestBody List<String> usernames) {

        return ResponseEntity.ok(githubAnalysisService.rankDevelopers(usernames));
    }

}
