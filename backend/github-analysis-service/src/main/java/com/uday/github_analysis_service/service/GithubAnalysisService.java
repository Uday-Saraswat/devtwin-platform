package com.uday.github_analysis_service.service;

import java.util.Map;

public interface GithubAnalysisService {

    public Map<String, Object> analyzeProfile(String username);

}
