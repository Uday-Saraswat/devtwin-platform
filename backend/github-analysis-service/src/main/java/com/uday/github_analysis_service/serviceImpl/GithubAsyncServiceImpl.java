package com.uday.github_analysis_service.serviceImpl;

import com.uday.github_analysis_service.client.GithubClient;
import com.uday.github_analysis_service.dto.GithubRepoResponse;
import com.uday.github_analysis_service.dto.GithubUserResponse;
import com.uday.github_analysis_service.service.GithubAsyncService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class GithubAsyncServiceImpl implements GithubAsyncService {

    private final GithubClient githubClient;

    @Override
    @Async
    @Retry(name = "githubApi")
    public CompletableFuture<GithubUserResponse> getGithubUserAsync(String username) {
        System.out.println(Thread.currentThread().getName());
        GithubUserResponse user = githubClient.getUser(username);
        return CompletableFuture.completedFuture(user);
    }

    @Override
    @Async
    @Retry(name = "githubApi")
    public CompletableFuture<List<GithubRepoResponse>> getGithubReposAsync(String username) {
        System.out.println(Thread.currentThread().getName());
        List<GithubRepoResponse> repos = githubClient.getUserRepositories(username);
        return CompletableFuture.completedFuture(repos);
    }
}
