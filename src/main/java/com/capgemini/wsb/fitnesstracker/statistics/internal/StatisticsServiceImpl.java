package com.capgemini.wsb.fitnesstracker.statistics.internal;


import com.capgemini.wsb.fitnesstracker.statistics.internal.StatisticsServiceImpl;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StatisticsServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);
    private final TrainingProvider trainingProvider;
    private final UserProvider userProvider;
    private final EmailSender emailSender;
    private final TrainingRepository trainingRepository;
    private final StatisticRepository statisticRepository;

    @Autowired
    public StatisticsServiceImpl(TrainingProvider trainingProvider, UserProvider userProvider, EmailSender emailSender, TrainingRepository trainingRepository, StatisticRepository statisticRepository) {
        this.trainingProvider = trainingProvider;
        this.userProvider = userProvider;
        this.emailSender = emailSender;
        this.trainingRepository = trainingRepository;
        this.statisticRepository = statisticRepository;
    }

    public void generateAndSendMonthlyReport(Long userId, int month, int year) {
        try {
            logger.info("Rozpoczynamy generowanie raportu dla użytkownika: {}", userId);

            User user = userProvider.getUser(userId)
                    .orElseThrow(() -> {
                        logger.error("Użytkownik nie znaleziony: {}", userId);
                        return new IllegalArgumentException("User not found for ID: " + userId);
                    });
            logger.info("Znaleziono użytkownika: {}", user.getFirstName());

            List<Training> trainings = trainingProvider.getTrainingsByUserId(userId)
                    .stream()
                    .filter(training -> isInMonth(training, month, year))
                    .collect(Collectors.toList());
            logger.info("Znaleziono {} treningów", trainings.size());

            String report = generateTextReport(user, trainings, month, year);
            logger.info("Raport wygenerowany");

            sendReportViaEmail(user, report);
            logger.info("Raport wysłany do: {}", user.getEmail());
        } catch (Exception e) {
            logger.error("Błąd podczas generowania raportu: ", e);
            throw e; // Rzucenie wyjątku dalej, aby można było go śledzić
        }
    }

    private boolean isInMonth(Training training, int month, int year) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String trainingDate = sdf.format(training.getStartTime());
        String targetDate = year + "-" + (month < 10 ? "0" + month : month);
        return trainingDate.equals(targetDate);
    }

    private String generateTextReport(User user, List<Training> trainings, int month, int year) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        StringBuilder report = new StringBuilder();
        report.append("Raport treningów dla użytkownika: ").append(user.getFirstName()).append(" ").append(user.getLastName()).append("\n");
        report.append("Miesiąc: ").append(LocalDate.of(year, month, 1).format(formatter)).append("\n\n");

        if (trainings.isEmpty()) {
            report.append("Brak zarejestrowanych treningów w tym miesiącu.\n");
            return report.toString();
        }

        report.append("Szczegóły treningów:\n");
        report.append("=========================\n");

        trainings.forEach(training -> {
            report.append("Data: ").append(new SimpleDateFormat("dd-MM-yyyy").format(training.getStartTime())).append("\n");
            report.append("Typ treningu: ").append(training.getActivityType()).append("\n");
            report.append("Dystans: ").append(training.getDistance()).append(" km\n");
            report.append("-------------------------\n");
        });

        report.append("=========================\n");
        report.append("Łączna liczba treningów: ").append(trainings.size()).append("\n");
        double totalDistance = trainings.stream().mapToDouble(Training::getDistance).sum();
        report.append("Łączny dystans: ").append(totalDistance).append(" km\n");


        return report.toString();
    }

    private void sendReportViaEmail(User user, String report) {
        String subject = "Raport treningów za " + new SimpleDateFormat("MMMM yyyy").format(new java.util.Date());
        String to = user.getEmail();

        // Tworzenie EmailDto z danymi
        EmailDto emailDto = new EmailDto(to, subject, report);

        // Wysyłanie e-maila przez EmailSender
        emailSender.send(emailDto);
    }

    // Metoda getReportContent, która zwraca raport jako tekst
    public String getReportContent(Long userId, int month, int year) {
        // Tworzenie formatu "yyyy-MM"
        LocalDate startDate = LocalDate.of(year, month, 1);  // Pierwszy dzień miesiąca
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());  // Ostatni dzień miesiąca

        // Pobranie użytkownika
        User user = userProvider.getUser(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + userId));

        // Pobranie treningów na podstawie roku, miesiąca i ID użytkownika
        List<Training> trainings = trainingRepository.findByUserIdAndDateBetween(userId, startDate, endDate);

        // Generowanie raportu na podstawie treningów
        return generateTextReport(user, trainings, month, year);
    }




    // Dodanie metody, która zwraca wszystkie dostępne raporty
    public List<Statistics> getAllReports() {
        // Pobranie raportów z repozytorium (można również dodać filtrację, sortowanie itp.)
        return statisticRepository.findAll();
    }
}
