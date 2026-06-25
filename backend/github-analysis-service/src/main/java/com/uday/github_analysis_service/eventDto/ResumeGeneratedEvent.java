package com.uday.github_analysis_service.eventDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeGeneratedEvent {

    private String username;

    private Integer developerScore;

    private String developerLevel;
}
