package com.capgemini.wsb.fitnesstracker.statistics.internal;


import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDate;

public interface StatisticRepository extends JpaRepository<Statistics, Long> {
//    List<Statistics> findByUserIdAndMonthAndYear(Long userId, int month, int year);
}
