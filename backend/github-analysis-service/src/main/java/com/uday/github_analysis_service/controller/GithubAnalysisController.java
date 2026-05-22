package com.uday.github_analysis_service.controller;


import com.uday.github_analysis_service.dto.CommitConsistencyResponse;
import com.uday.github_analysis_service.service.GithubAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
