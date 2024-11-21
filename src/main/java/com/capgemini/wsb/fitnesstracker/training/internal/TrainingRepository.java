package com.capgemini.wsb.fitnesstracker.training.internal;
import com.capgemini.wsb.fitnesstracker.training.api.Training;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Month;
import java.util.List;
import java.time.LocalDate;


public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

}
