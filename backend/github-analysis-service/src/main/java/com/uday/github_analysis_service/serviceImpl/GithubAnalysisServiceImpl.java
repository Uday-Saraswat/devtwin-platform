package com.uday.github_analysis_service.serviceImpl;


import com.uday.github_analysis_service.client.GithubClient;
import com.uday.github_analysis_service.dto.*;
import com.uday.github_analysis_service.exception.GithubApiException;
import com.uday.github_analysis_service.exception.ResourceNotFoundException;
import com.uday.github_analysis_service.service.GithubAnalysisService;
import com.uday.github_analysis_service.service.GithubAsyncService;
import com.uday.github_analysis_service.service.ResumeCacheService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class GithubAnalysisServiceImpl implements GithubAnalysisService {

    private final GithubClient githubClient;

    private final GithubAsyncService githubAsyncService;

    @Override
    public Map<String, Object> analyzeProfile(String username) {

        List<GithubRepoResponse> repos;
        List<GithubEventResponse> events;

        try {
            repos = githubClient.getUserRepositories(username);
            events = githubClient.getUserEvents(username);
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("GitHub user not found: " + username);

        } catch (FeignException.Forbidden ex) {
            throw new GithubApiException("GitHub API rate limit exceeded");

        } catch (FeignException ex) {
            throw new GithubApiException("GitHub API is unavailable");

        } catch (Exception ex) {
            throw new GithubApiException("Unexpected error occurred");
        }

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

//        List<GithubEventResponse> events = githubClient.getUserEvents(username);

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

    @Override
    public ResumeResponse buildResume(String username) {

        System.out.println("Calling GitHub API...");
        GithubUserResponse user;
        List<GithubRepoResponse> repos;

        CompletableFuture<GithubUserResponse> userFuture = githubAsyncService.getGithubUserAsync(username);

        CompletableFuture<List<GithubRepoResponse>> repoFuture = githubAsyncService.getGithubReposAsync(username);

        try {
            user = userFuture.join();
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof FeignException.NotFound) {
                throw new ResourceNotFoundException("GitHub user not found: " + username);
            }
            throw new GithubApiException("Failed to fetch GitHub user");
        }


        try {
            repos = repoFuture.join();
        } catch (Exception e) {
            throw new GithubApiException("Failed to fetch repositories");
        }

        //TOP REPOSITORIES
        List<String> topRepositories = repos.stream().sorted((repo1, repo2) ->
                        repo2.getStargazers_count() - repo1.getStargazers_count()).limit(5)
                .map(GithubRepoResponse::getName).toList();

        //TOP LANGUAGES

        List<String> topLanguages = repos.stream().map(GithubRepoResponse::getLanguage).filter(Objects::nonNull)
                .distinct().toList();

        // DEVELOPER SCORE

        int totalStars = repos.stream().mapToInt(GithubRepoResponse::getStargazers_count).sum();
        int totalForks = repos.stream().mapToInt(GithubRepoResponse::getForks_count).sum();
        int developerScore = calculateDeveloperScore(repos.size(), totalStars, totalForks, topLanguages.size());

        // DEVELOPER LEVEL
        String developerLevel = getDeveloperLevel(developerScore);

        //BUILD RESPONSE

        return ResumeResponse.builder().name(user.getName()).githubUsername(user.getLogin())
                .bio(user.getBio()).publicRepos(user.getPublic_repos()).followers(user.getFollowers())
                .topLanguages(topLanguages).topRepositories(topRepositories)
                .developerLevel(developerLevel).developerScore(developerScore).build();

    }

    @Override
    @Cacheable(value = "commitConsistency", key = "#username")
    public CommitConsistencyResponse getCommitConsistency(String username) {
        List<GithubEventResponse> events;
        try {
            events = githubClient.getUserEvents(username);
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("GitHub user not found");
        } catch (FeignException.Forbidden ex) {
            throw new GithubApiException("GitHub API rate limit exceeded");
        } catch (FeignException ex) {
            throw new GithubApiException("GitHub API unavailable");
        }
        //Total Events
        int totalEvents = events.size();

        //ACTIVE DAYS (unique dates)
        Set<String> activeDaysSet = new HashSet<>();
        int commitCount = 0;
        for (GithubEventResponse event : events) {

            if ("PushEvent".equalsIgnoreCase(event.getType())) {

                commitCount++; // each push = activity unit

                activeDaysSet.add(event.getCreated_at().substring(0, 10));
            }
        }

        int activeDays = activeDaysSet.size();

        // AVERAGE COMMITS PER DAY
        double avgCommits = (activeDays == 0 ? 0 : (double) commitCount / activeDays);

        //CONSISTENCY SCORE (simple formula)
        int consistencyScore = Math.min(100, (activeDays * 6) + (commitCount * 3));

        return CommitConsistencyResponse.builder()
                .username(username)
                .totalEvents(totalEvents)
                .activeDays(activeDays)
                .averageCommitsPerDay(avgCommits)
                .consistencyScore(consistencyScore)
                .build();
    }

    @Override
    public byte[] generateResumePdf(String username) {

        ResumeResponse resume = buildResume(username);

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            //TITLE
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Paragraph title = new Paragraph("GitHub Developer Resume", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            //BASIC DETAILS
            document.add(new Paragraph("Name: " + resume.getName()));

            document.add(new Paragraph("GitHub Username: " + resume.getGithubUsername()));

            document.add(new Paragraph("Bio: " + resume.getBio()));

            document.add(new Paragraph("Followers: " + resume.getFollowers()));

            document.add(new Paragraph("Public Repositories: " + resume.getPublicRepos()));

            document.add(new Paragraph("Developer Score: " + resume.getDeveloperScore()));

            document.add(new Paragraph("Developer Level: " + resume.getDeveloperLevel()));

            document.add(new Paragraph(" "));

            //TOP LANGUAGES
            document.add(new Paragraph("Top Languages:"));

            for (String language : resume.getTopLanguages()) {
                document.add(new Paragraph("- " + language));
            }
            document.add(new Paragraph(" "));

            //TOP REPOSITORIES
            document.add(new Paragraph("Top Repositories:"));

            for (String repo : resume.getTopRepositories()) {
                document.add(new Paragraph("- " + repo));
            }

            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new GithubApiException("Failed to generate PDF resume");
        }
    }

    @Override
    public List<DeveloperRankingResponse> rankDevelopers(List<String> usernames) {

        List<DeveloperRankingResponse> rankings = new ArrayList<>();

        for (String username : usernames) {

            try {

                ResumeResponse resume = buildResume(username);
                rankings.add(DeveloperRankingResponse.builder()
                        .username(username)
                        .score(resume.getDeveloperScore())
                        .developerLevel(resume.getDeveloperLevel()).rank(0).build());

            } catch (Exception e) {
                throw new GithubApiException("Failed to rank developers");
            }

        }

//        SORT BY SCORE DESC

        rankings.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

//        ASSIGN Ranks

        for (int i = 0; i < rankings.size(); i++) {
            rankings.get(i).setRank(i + 1);
        }

        return rankings;
    }
}
