package com.uday.github_analysis_service.serviceImpl;

import com.uday.github_analysis_service.client.GithubClient;
import com.uday.github_analysis_service.dto.GithubRepoResponse;
import com.uday.github_analysis_service.service.GithubAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GithubAnalysisServiceImpl implements GithubAnalysisService {

    private final GithubClient githubClient;

    @Override
    public Map<String, Object> analyzeProfile(String username) {
        List<GithubRepoResponse> repos = githubClient.getUserRepositories(username);
        int totalRepos = repos.size();
        int totalStars = repos.stream().mapToInt(GithubRepoResponse::getStargazers_count).sum();
        int totalForks = repos.stream().mapToInt(GithubRepoResponse::getForks_count).sum();

        Map<String, Integer> languages = new HashMap<>();

        for (GithubRepoResponse repo : repos) {
            if (repo.getLanguage() != null) {
                languages.put(repo.getLanguage(), languages.getOrDefault(repo.getLanguage(), 0) + 1);
            }
        }

        Map<String, Object> response = new HashMap<>();

        response.put("username", username);
        response.put("totalRepositories", totalRepos);
        response.put("totalStars", totalStars);
        response.put("totalForks", totalForks);
        response.put("languagesUsed", languages);

        return response;
    }
}
