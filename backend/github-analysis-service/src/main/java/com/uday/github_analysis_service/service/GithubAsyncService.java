package com.uday.github_analysis_service.service;

import com.uday.github_analysis_service.dto.GithubRepoResponse;
import com.uday.github_analysis_service.dto.GithubUserResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GithubAsyncService {

    public CompletableFuture<GithubUserResponse> getGithubUserAsync(String username);

    public CompletableFuture<List<GithubRepoResponse>> getGithubReposAsync(String username);
}
