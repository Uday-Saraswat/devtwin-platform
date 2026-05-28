package com.uday.github_analysis_service.controller;


import com.uday.github_analysis_service.dto.CommitConsistencyResponse;
import com.uday.github_analysis_service.dto.DeveloperRankingResponse;
import com.uday.github_analysis_service.service.GithubAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/github")
@RequiredArgsConstructor
public class GithubAnalysisController {

    private final GithubAnalysisService githubAnalysisService;

    @GetMapping("/analyze/{username}")
    public ResponseEntity<?> analyzeGithubProfile(@PathVariable String username) {

        return ResponseEntity.ok(githubAnalysisService.analyzeProfile(username));
    }

    @GetMapping("/resume/{username}")
    public ResponseEntity<?> generateResume(@PathVariable String username) {
        return ResponseEntity.ok(githubAnalysisService.generateResume(username));
    }

    @GetMapping("/analytics/commit-consistency/{username}")
    public ResponseEntity<CommitConsistencyResponse> getCommitConsistency(@PathVariable String username) {
        return ResponseEntity.ok(githubAnalysisService.getCommitConsistency(username));
    }

    @GetMapping("/resume/pdf/{username}")
    public ResponseEntity<byte[]> generateResumePdf(@PathVariable String username) {
        byte[] pdf = githubAnalysisService.generateResumePdf(username);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=resume.pdf")
                .contentType(MediaType.APPLICATION_PDF).body(pdf);
    }

    @PostMapping("/rank")
    public ResponseEntity<List<DeveloperRankingResponse>> rankDevelopers(@RequestBody List<String> usernames) {

        return ResponseEntity.ok(githubAnalysisService.rankDevelopers(usernames));
    }

}
