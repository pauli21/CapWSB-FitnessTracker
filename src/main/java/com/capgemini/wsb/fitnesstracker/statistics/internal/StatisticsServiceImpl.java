package com.capgemini.wsb.fitnesstracker.statistics.internal;


import com.capgemini.wsb.fitnesstracker.statistics.internal.StatisticsServiceImpl;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticService;
import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StatisticsServiceImpl implements StatisticService {
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


    /**
     * Tworzy nową statystykę i zapisuje ją w bazie danych.
     *
     * @param statistics Statystyka do utworzenia.
     * @return Utworzona statystyka.
     */
    @Override
    public Statistics createStatistics(Statistics statistics) {
        return statisticRepository.save(statistics);
    }

    /**
     * Aktualizuje istniejącą statystykę na podstawie jej identyfikatora.
     *
     * @param id Identyfikator statystyki do zaktualizowania.
     * @param updatedStatistics Zaktualizowane dane statystyki.
     * @return Zaktualizowana statystyka.
     */
    @Override
    public Statistics updateStatistics(Long id, Statistics updatedStatistics) {
        Statistics existing = statisticRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Statistics not found for ID: " + id));
        existing.setTotalTrainings(updatedStatistics.getTotalTrainings());
        existing.setTotalDistance(updatedStatistics.getTotalDistance());
        existing.setTotalCaloriesBurned(updatedStatistics.getTotalCaloriesBurned());
        return statisticRepository.save(existing);
    }

    /**
     * Pobiera wszystkie statystyki przypisane do użytkownika o danym ID.
     *
     * @param userId Identyfikator użytkownika.
     * @return Lista statystyk przypisanych do użytkownika.
     */
    @Override
    public List<Statistics> getStatisticsByUserId(Long userId) {
        return statisticRepository.findAll().stream()
                .filter(stats -> stats.getUser().getId().equals(userId))
                .toList();
    }

    /**
     * Usuwa statystykę o podanym identyfikatorze.
     *
     * @param id Identyfikator statystyki do usunięcia.
     */
    @Override
    public void deleteStatistics(Long id) {
        statisticRepository.deleteById(id);
    }

    /**
     * Pobiera statystyki, w których liczba spalonych kalorii jest większa niż podana wartość.
     *
     * @param calories Liczba kalorii, dla których statystyki mają być zwrócone.
     * @return Lista statystyk, gdzie liczba spalonych kalorii jest większa niż podana wartość.
     */
    @Override
    public List<Statistics> getStatisticsByCaloriesGreaterThan(int calories) {
        return statisticRepository.findAll().stream()
                .filter(stats -> stats.getTotalCaloriesBurned() > calories)
                .toList();
    }


    /**
     * Pobiera wszystkie dostępne raporty.
     *
     * @return Lista wszystkich raportów.
     */
    @Override
    public List<Statistics> getAllReports() {
        return statisticRepository.findAll();
    }


    /**
     * Generuje raport w formie tekstowej na podstawie danych o treningach i użytkowniku.
     *
     * @param userId Identyfikator użytkownika.
     * @param month Miesiąc raportu.
     * @param year Rok raportu.
     * @return Treść raportu w formie tekstu.
     */    @Override
    public String getReportContent(Long userId, int month, int year) {
        LocalDate startDate = LocalDate.of(year, month, 1);  // Pierwszy dzień miesiąca
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());  // Ostatni dzień miesiąca

        User user = userProvider.getUser(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + userId));

        List<Training> trainings = trainingRepository.findByUserIdAndDateBetween(userId, startDate, endDate);

        return generateTextReport(user, trainings, month, year);
    }

    /**
     * Generuje i wysyła miesięczny raport na e-mail użytkownika.
     *
     * @param userId Identyfikator użytkownika.
     * @param month Miesiąc raportu.
     * @param year Rok raportu.
     */
    @Override
    public void generateAndSendMonthlyReport(Long userId, int month, int year) {
        User user = userProvider.getUser(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + userId));

        List<Training> trainings = trainingProvider.getTrainingsByUserId(userId)
                .stream()
                .filter(training -> isInMonth(training, month, year))
                .collect(Collectors.toList());

        String report = generateTextReport(user, trainings, month, year);

        EmailDto emailDto = new EmailDto(user.getEmail(), "Monthly Report", report);
        emailSender.send(emailDto);
    }

    /**
     * Sprawdza, czy trening odbył się w danym miesiącu i roku.
     *
     * @param training Trening do sprawdzenia.
     * @param month Miesiąc, w którym trening powinien się odbyć.
     * @param year Rok, w którym trening powinien się odbyć.
     * @return True, jeśli trening odbył się w danym miesiącu i roku, w przeciwnym razie False.
     */
    private boolean isInMonth(Training training, int month, int year) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String trainingDate = sdf.format(training.getStartTime());
        return trainingDate.equals(year + "-" + String.format("%02d", month));
    }

    /**
     * Generuje raport w formie tekstowej na podstawie użytkownika i jego treningów.
     *
     * @param user Użytkownik, dla którego generowany jest raport.
     * @param trainings Lista treningów użytkownika.
     * @param month Miesiąc raportu.
     * @param year Rok raportu.
     * @return Raport w postaci tekstu.
     */
    private String generateTextReport(User user, List<Training> trainings, int month, int year) {
        StringBuilder report = new StringBuilder();
        report.append("Report for ").append(user.getFirstName()).append(" ").append(user.getLastName()).append("\n");
        report.append("Month: ").append(month).append("/").append(year).append("\n");

        if (trainings.isEmpty()) {
            report.append("No trainings found for this month.\n");
        } else {
            trainings.forEach(training -> report.append(training.toString()).append("\n"));
        }

        return report.toString();
    }




}
