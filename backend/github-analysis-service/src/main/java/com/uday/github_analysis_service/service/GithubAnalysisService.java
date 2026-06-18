package com.uday.github_analysis_service.service;

import com.uday.github_analysis_service.dto.CommitConsistencyResponse;
import com.uday.github_analysis_service.dto.DeveloperRankingResponse;
import com.uday.github_analysis_service.dto.ResumeResponse;

import java.util.List;
import java.util.Map;

public interface GithubAnalysisService {

    public Map<String, Object> analyzeProfile(String username);

    public ResumeResponse buildResume(String username);

    public CommitConsistencyResponse getCommitConsistency(String username);

    public byte[] generateResumePdf(String username);

    public List<DeveloperRankingResponse> rankDevelopers(List<String> usernames);

}
