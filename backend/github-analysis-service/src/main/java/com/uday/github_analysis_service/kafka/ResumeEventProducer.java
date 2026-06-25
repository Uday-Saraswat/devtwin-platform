package com.uday.github_analysis_service.kafka;

import com.uday.github_analysis_service.eventDto.ResumeGeneratedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeEventProducer {

    private final KafkaTemplate<String, ResumeGeneratedEvent> kafkaTemplate;

    public void publishResumeGeneratedEvent(ResumeGeneratedEvent event){
        kafkaTemplate.send("resume-generated-topic", event.getUsername(), event);
        System.out.println("EVENT PUBLISHED : " + event.getUsername());
    }
}
