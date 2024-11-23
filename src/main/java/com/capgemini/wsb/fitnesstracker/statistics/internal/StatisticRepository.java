package com.capgemini.wsb.fitnesstracker.statistics.internal;


import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDate;

/**
 * Repozytorium do zarządzania encjami statystyk w bazie danych.
 * Rozszerza JpaRepository, umożliwiając operacje CRUD na tabeli "statistics".
 */
public interface StatisticRepository extends JpaRepository<Statistics, Long> {
//    List<Statistics> findByUserIdAndMonthAndYear(Long userId, int month, int year);
}
