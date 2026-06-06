package com.uday.github_analysis_service.service;

import com.uday.github_analysis_service.dto.ResumeResponse;

public interface ResumeCacheService {
    ResumeResponse generateResume(String username);
}
