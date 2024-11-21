package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class TrainingMapper {

    // Konwersja z TrainingDto do Training
    public static Training toEntity(TrainingDto dto) {
        Training training = new Training(
                dto.getUser(),
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getActivityType(),
                dto.getDistance(),
                dto.getAverageSpeed()
        );

        // Jeśli dto.getDate() jest Stringiem w formacie "yyyy-MM", należy go przekonwertować na LocalDate
        if (dto.getDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            training.setDate(LocalDate.parse(dto.getDate(), formatter));
        }

        return training;
    }


    public static TrainingDto toDto(Training training) {
        TrainingDto dto = new TrainingDto();
        dto.setUser(training.getUser());
        dto.setStartTime(training.getStartTime());
        dto.setEndTime(training.getEndTime());
        dto.setActivityType(training.getActivityType());
        dto.setDistance(training.getDistance());
        dto.setAverageSpeed(training.getAverageSpeed());

        // Konwersja z LocalDate na String w formacie "yyyy-MM"
        if (training.getDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            dto.setDate(training.getDate().format(formatter));
        }

        return dto;
    }


}