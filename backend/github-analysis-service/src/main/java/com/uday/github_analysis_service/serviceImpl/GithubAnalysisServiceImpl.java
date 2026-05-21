package com.uday.github_analysis_service.serviceImpl;

import com.uday.github_analysis_service.client.GithubClient;
import com.uday.github_analysis_service.dto.GithubEventResponse;
import com.uday.github_analysis_service.dto.GithubRepoResponse;
import com.uday.github_analysis_service.service.GithubAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

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

        int developerScore = calculateDeveloperScore(totalRepos, totalStars,
                totalForks, languages.size());

        String level = getDeveloperLevel(developerScore);

        List<String> strengths = generateStrengths(totalRepos, totalStars, languages.size());

        List<GithubEventResponse> events = githubClient.getUserEvents(username);

        Map<String, Object> consistencyData = analyzeConsistency(events);

        Map<String, Object> response = new HashMap<>();

        response.put("username", username);
        response.put("totalRepositories", totalRepos);
        response.put("totalStars", totalStars);
        response.put("totalForks", totalForks);
        response.put("languagesUsed", languages);
        response.put("developerScore", developerScore);
        response.put("developerLevel", level);
        response.put("strengths", strengths);
        response.putAll(consistencyData);

        return response;
    }

    private int calculateDeveloperScore(int repos, int stars, int forks, int languageCount) {

        int score = 0;
        score += repos * 2;
        score += stars * 3;
        score += forks * 2;
        score += languageCount * 5;

        if (score > 100) {
            score = 100;
        }

        return score;
    }

    private String getDeveloperLevel(int score) {

        if (score >= 80) {
            return "Advanced Developer";
        }

        if (score >= 50) {
            return "Intermediate Developer";
        }

        return "Beginner Developer";
    }

    private List<String> generateStrengths(int repos, int stars, int languageCount) {

        List<String> strengths = new ArrayList<>();

        if (repos >= 10) {
            strengths.add("Good Repository Activity");
        }

        if (stars >= 5) {
            strengths.add("Popular Repositories");
        }

        if (languageCount >= 3) {
            strengths.add("Good Language Diversity");
        }

        return strengths;
    }

    private Map<String, Object> analyzeConsistency(List<GithubEventResponse> events) {

        int totalCommits = 0;
        Set<String> activeDays = new HashSet<>();

        for (GithubEventResponse event : events) {
            if ("PushEvent".equals(event.getType())) {
                totalCommits++;
                activeDays.add(event.getCreated_at().substring(0, 10));
            }
        }

        int consistencyScore = Math.min(100, (totalCommits * 2) + (activeDays.size() * 3));

        String developerType;

        if (consistencyScore >= 80) {
            developerType = "Highly Consistent Developer";
        } else if (consistencyScore >= 50) {
            developerType = "Consistent Contributor";
        } else {
            developerType = "Occasional Contributor";
        }

        Map<String, Object> response = new HashMap<>();

        response.put("consistencyScore", consistencyScore);
        response.put("totalCommitsThisMonth", totalCommits);
        response.put("activeDays", activeDays.size());
        response.put("developerType", developerType);


        return response;
    }

}
