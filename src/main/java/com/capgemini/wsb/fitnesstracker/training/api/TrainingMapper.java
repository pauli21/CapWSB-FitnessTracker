package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

/**
 * Klasa mapująca dane między obiektami {@link TrainingDto} a {@link Training}.
 */
public class TrainingMapper {

    /**
     * Konwertuje obiekt {@link TrainingDto} na obiekt {@link Training}.
     *
     * @param dto obiekt DTO do przekonwertowania
     * @return obiekt encji {@link Training}
     */    public static Training toEntity(TrainingDto dto) {
        Training training = new Training(
                dto.getUser(),
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getActivityType(),
                dto.getDistance(),
                dto.getAverageSpeed()
        );

        if (dto.getDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            training.setDate(LocalDate.parse(dto.getDate(), formatter));
        }

        return training;
    }

    /**
     * Konwertuje obiekt {@link Training} na obiekt {@link TrainingDto}.
     *
     * @param training obiekt encji do przekonwertowania
     * @return obiekt DTO {@link TrainingDto}
     */
    public static TrainingDto toDto(Training training) {
        TrainingDto dto = new TrainingDto();
        dto.setUser(training.getUser());
        dto.setStartTime(training.getStartTime());
        dto.setEndTime(training.getEndTime());
        dto.setActivityType(training.getActivityType());
        dto.setDistance(training.getDistance());
        dto.setAverageSpeed(training.getAverageSpeed());

        if (training.getDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            dto.setDate(training.getDate().format(formatter));
        }

        return dto;
    }


}