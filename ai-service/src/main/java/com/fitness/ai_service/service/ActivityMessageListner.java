package com.fitness.ai_service.service;

import com.fitness.ai_service.model.Activity;
import com.fitness.ai_service.model.Recommendation;
import com.fitness.ai_service.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListner {

    private final ActivityAiService activityAiService;
    private final RecommendationRepository recommendationRepository;

    @RabbitListener(queues = "activity.queue")
    public void processActivity(Activity activity){
        log.info("Receive Activity for processing: {}", activity.getId());
        // log.info("Generated Recommendation: {}", activityAiService.generateRecommendation(activity));
        Recommendation recommendation = activityAiService.generateRecommendation(activity);
        recommendationRepository.save(recommendation);
    }
}
