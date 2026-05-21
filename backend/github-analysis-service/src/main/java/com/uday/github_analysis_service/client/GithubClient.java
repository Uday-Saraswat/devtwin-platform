package com.uday.github_analysis_service.client;

import com.uday.github_analysis_service.config.GithubFeignConfig;
import com.uday.github_analysis_service.dto.GithubEventResponse;
import com.uday.github_analysis_service.dto.GithubRepoResponse;
import com.uday.github_analysis_service.dto.GithubUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "github-client", url = "https://api.github.com", configuration = GithubFeignConfig.class)
public interface GithubClient {

    @GetMapping("/users/{username}/repos")
    List<GithubRepoResponse> getUserRepositories(@PathVariable String username);

    @GetMapping("/users/{username}/events")
    List<GithubEventResponse> getUserEvents(@PathVariable String username);

    @GetMapping("/users/{username}")
    GithubUserResponse getUser(@PathVariable String username);

}
