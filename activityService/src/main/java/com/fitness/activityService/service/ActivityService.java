package com.fitness.activityService.service;

import com.fitness.activityService.dto.ActivityRequest;
import com.fitness.activityService.dto.ActivityResponse;
import com.fitness.activityService.model.Activity;
import com.fitness.activityService.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityResponse trackActivity(ActivityRequest request) {
        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMatrics(request.getAdditionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);
        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity activity){
       ActivityResponse response = new ActivityResponse();
       response.setId(activity.getId());
       response.setUserId(activity.getUserId());
       response.setType(activity.getType());
       response.setDuration(activity.getDuration());
       response.setCaloriesBurned(activity.getCaloriesBurned());
       response.setStartTime(activity.getStartTime());
       response.setAdditionalMatrics(activity.getAdditionalMatrics());
       response.setCreatedAt(activity.getCreatedAt());
       response.setUpdatedAt(activity.getUpdatedAt());
       return response;
    }

    public List<ActivityResponse> showActivities(String userId) {
        List<Activity> activities = activityRepository.findByUserId(userId);
        return activities.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public ActivityResponse getActivityById(String activityId) {
        return activityRepository.findById(activityId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Activity Not Found with id: " + activityId));
    }
}
