package com.uday.github_analysis_service.serviceImpl;

import com.uday.github_analysis_service.dto.ResumeResponse;
import com.uday.github_analysis_service.service.GithubAnalysisService;
import com.uday.github_analysis_service.service.ResumeCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeCacheServiceImpl implements ResumeCacheService {
    private final GithubAnalysisService githubAnalysisService;

    @Override
    @Cacheable(value = "resume", key = "#username")
    public ResumeResponse generateResume(String username) {
        return githubAnalysisService.buildResume(username);
    }
}
