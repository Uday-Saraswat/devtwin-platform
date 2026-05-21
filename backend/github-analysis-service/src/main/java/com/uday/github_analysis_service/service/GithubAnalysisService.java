package com.uday.github_analysis_service.service;

import com.uday.github_analysis_service.dto.ResumeResponse;

import java.util.Map;

public interface GithubAnalysisService {

    public Map<String, Object> analyzeProfile(String username);

    public ResumeResponse generateResume(String username);

}
