package com.capgemini.wsb.fitnesstracker.statistics.api;

import com.capgemini.wsb.fitnesstracker.statistics.internal.StatisticsServiceImpl;
import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

/**
 * Kontroler API odpowiedzialny za zarządzanie statystykami użytkowników.
 * Umożliwia pobieranie, tworzenie, aktualizowanie i usuwanie statystyk.
 * Obsługuje także generowanie raportów i ich wysyłanie na e-mail.
 */
@Slf4j
@RestController
@RequestMapping("/statistics")
public class StatisticController {
    private final StatisticService statisticService;

    /**
     * Konstruktor kontrolera, wstrzykuje zależność do serwisu statystyk.
     *
     * @param statisticService serwis odpowiedzialny za logikę statystyk.
     */
    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    /**
     * Pobiera wszystkie raporty.
     *
     * @return Lista raportów lub status 204, jeśli brak raportów.
     */
    @GetMapping
    public ResponseEntity<List<Statistics>> getAllReports() {
        List<Statistics> reports = statisticService.getAllReports(); // Pobranie wszystkich raportów

        if (reports.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(reports);
    }

    /**
     * Tworzy nową statystykę.
     *
     * @param statistics Obiekt statystyki.
     * @return Utworzona statystyka.
     */
    @PostMapping
    public ResponseEntity<Statistics> createStatistics(@RequestBody Statistics statistics) {
        Statistics created = statisticService.createStatistics(statistics);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Aktualizuje istniejącą statystykę.
     *
     * @param id          Identyfikator statystyki do aktualizacji.
     * @param statistics  Nowe dane statystyki.
     * @return Zaktualizowana statystyka.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Statistics> updateStatistics(@PathVariable Long id, @RequestBody Statistics statistics) {
        Statistics updated = statisticService.updateStatistics(id, statistics);
        return ResponseEntity.ok(updated);
    }

    /**
     * Pobiera statystyki dla użytkownika o danym ID.
     *
     * @param userId Identyfikator użytkownika.
     * @return Lista statystyk użytkownika.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Statistics>> getStatisticsByUserId(@PathVariable Long userId) {
        List<Statistics> statistics = statisticService.getStatisticsByUserId(userId);
        if (statistics.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(statistics);
    }

    /**
     * Usuwa statystykę o podanym ID.
     *
     * @param id Identyfikator statystyki.
     * @return Status 204, jeśli usunięcie zakończyło się sukcesem.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatistics(@PathVariable Long id) {
        statisticService.deleteStatistics(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Pobiera statystyki, gdzie liczba spalonych kalorii jest większa niż podana.
     *
     * @param calories Liczba kalorii.
     * @return Lista statystyk spełniających kryterium.
     */
    @GetMapping("/calories/greaterThan/{calories}")
    public ResponseEntity<List<Statistics>> getStatisticsByCaloriesGreaterThan(@PathVariable int calories) {
        List<Statistics> statistics = statisticService.getStatisticsByCaloriesGreaterThan(calories);
        if (statistics.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(statistics);
    }


    /**
     * Pobiera treść raportu dla użytkownika i miesiąca.
     *
     * @param userId Identyfikator użytkownika.
     * @param month  Miesiąc raportu.
     * @param year   Rok raportu.
     * @return Treść raportu lub status 404, jeśli brak raportu.
     */
    @GetMapping("/download/{userId}")
    public ResponseEntity<String> downloadReport(@PathVariable Long userId, @RequestParam int month, @RequestParam int year) {
        String reportContent = statisticService.getReportContent(userId, month, year);
        if (reportContent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Brak raportu dla tego użytkownika.");
        }
        return ResponseEntity.ok(reportContent);
    }

    /**
     * Generuje raport i wysyła go na e-mail użytkownika.
     *
     * @param userId Identyfikator użytkownika.
     * @param month  Miesiąc raportu.
     * @param year   Rok raportu.
     */
    @PostMapping("/generate/{userId}")
    public ResponseEntity<Void> generateAndSendReport(@PathVariable Long userId, @RequestParam int month, @RequestParam int year) {
        statisticService.generateAndSendMonthlyReport(userId, month, year);
        return ResponseEntity.ok().build();
    }

    private void sendReportViaEmail(Long userId, String report) {
    }




}
