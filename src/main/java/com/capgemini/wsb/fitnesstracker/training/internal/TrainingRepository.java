package com.capgemini.wsb.fitnesstracker.training.internal;
import com.capgemini.wsb.fitnesstracker.training.api.Training;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Month;
import java.util.List;
import java.time.LocalDate;

/**
 * Repozytorium JPA dla zarządzania encjami {@link Training}.
 */
public interface TrainingRepository extends JpaRepository<Training, Long> {
    /**
     * Znajduje wszystkie treningi użytkownika w określonym przedziale dat.
     *
     * @param userId    identyfikator użytkownika, którego treningi mają zostać wyszukane
     * @param startDate data początkowa zakresu
     * @param endDate   data końcowa zakresu
     * @return lista treningów użytkownika w określonym przedziale dat
     */
    List<Training> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

}
