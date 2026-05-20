package com.uday.github_analysis_service.controller;


import com.uday.github_analysis_service.service.GithubAnalysisService;
import lombok.RequiredArgsConstructor;
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
    
}
