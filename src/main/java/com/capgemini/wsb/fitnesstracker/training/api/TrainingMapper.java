package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;

public class TrainingMapper {
    public static Training toEntity(TrainingDto dto) {
        return new Training(dto.getUser(), dto.getStartTime(), dto.getEndTime(),
                dto.getActivityType(), dto.getDistance(), dto.getAverageSpeed());
    }


    public static TrainingDto toDto(Training training) {
        TrainingDto dto = new TrainingDto();
        dto.setUser(training.getUser());
        dto.setStartTime(training.getStartTime());
        dto.setEndTime(training.getEndTime());
        dto.setActivityType(training.getActivityType());
        dto.setDistance(training.getDistance());
        dto.setAverageSpeed(training.getAverageSpeed());
        return dto;
    }
}