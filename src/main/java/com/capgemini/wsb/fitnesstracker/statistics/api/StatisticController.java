package com.capgemini.wsb.fitnesstracker.statistics.api;

import com.capgemini.wsb.fitnesstracker.statistics.internal.StatisticsServiceImpl;
import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/statistics")
public class StatisticController {
    private final StatisticsServiceImpl reportService;

    @Autowired
    public StatisticController(StatisticsServiceImpl reportService) {
        this.reportService = reportService;
    }

    // Metoda GET do pobierania wszystkich raportów
    @GetMapping
    public ResponseEntity<List<Statistics>> getAllReports() {
        List<Statistics> reports = reportService.getAllReports(); // Pobranie wszystkich raportów

        if (reports.isEmpty()) {
            // Jeśli brak raportów, zwróć status 204 No Content
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(reports);
        }

        // Jeśli raporty istnieją, zwróć je z kodem 200 OK
        return ResponseEntity.ok(reports);
    }

    @PostMapping("/generate/{userId}")
    public void generateReport(@PathVariable Long userId, @RequestParam int month, @RequestParam int year) {
        String report = reportService.getReportContent(userId, month, year);

        // Wysyłanie raportu poprzez email
        sendReportViaEmail(userId, report);
    }

    private void sendReportViaEmail(Long userId, String report) {
        // Zakładając, że masz metodę do wysyłania e-maili
        // Wyslij email z wygenerowanym raportem
    }

    // Metoda GET do pobierania raportu
    @GetMapping("/download/{userId}")
    public ResponseEntity downloadReport(@PathVariable Long userId, @RequestParam int month, @RequestParam int year) {
        // Pobieranie wygenerowanego raportu jako byte[]
        String reportContent = reportService.getReportContent(userId, month, year);

        if (reportContent == null) {
            // Jeśli raport nie istnieje, zwróć błąd
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Brak raportu dla tego użytkownika.");
        }


        return new ResponseEntity<>(reportContent, HttpStatus.OK);
    }
}
