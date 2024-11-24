package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;

import java.util.Optional;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Implementacja serwisu do zarządzania treningami.
 * Implementuje interfejs {@link TrainingProvider}.
 */
@Service
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;

    /**
     * Konstruktor inicjujący serwis {@link TrainingServiceImpl}.
     *
     * @param trainingRepository repozytorium treningów, które będzie używane do operacji na danych
     */
    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    /**
     * Pobiera wszystkie dostępne treningi.
     *
     * @return lista wszystkich treningów
     */
    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }


    /**
     * Pobiera treningi przypisane do użytkownika o podanym identyfikatorze.
     *
     * @param userId identyfikator użytkownika
     * @return lista treningów użytkownika
     */
    @Override
    public List<Training> getTrainingsByUserId(Long userId) {
        return trainingRepository.findAll().stream()
                .filter(training -> training.getUser().getId().equals(userId))
                .toList();
    }

    /**
     * Pobiera treningi zakończone po podanej dacie.
     *
     * @param date data, po której mają zostać wyszukane treningi
     * @return lista treningów zakończonych po podanej dacie
     */
    @Override
    public List<Training> getCompletedTrainingsAfter(Date date) {
        return trainingRepository.findAll().stream()
                .filter(training -> training.getEndTime().after(date))
                .toList();
    }

    /**
     * Pobiera treningi dla określonego typu aktywności.
     *
     * @param activityType typ aktywności treningu
     * @return lista treningów dla określonego typu aktywności
     */
    @Override
    public List<Training> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findAll().stream()
                .filter(training -> training.getActivityType() == activityType)
                .toList();
    }

    /**
     * Tworzy nowy trening na podstawie danych z obiektu DTO.
     *
     * @param trainingDto obiekt DTO zawierający dane treningu
     * @return utworzony obiekt treningu
     */
    @Override
    public Training createTraining(TrainingDto trainingDto) {
        Training training = new Training(
                trainingDto.getUser(),
                trainingDto.getStartTime(),
                trainingDto.getEndTime(),
                trainingDto.getActivityType(),
                trainingDto.getDistance(),
                trainingDto.getAverageSpeed()
        );
        return trainingRepository.save(training);
    }

    /**
     * Aktualizuje istniejący trening na podstawie jego identyfikatora i danych z obiektu DTO.
     *
     * @param id identyfikator treningu do zaktualizowania
     * @param trainingDto nowe dane treningu
     * @return zaktualizowany obiekt treningu
     * @throws TrainingNotFoundException jeśli trening o podanym ID nie istnieje
     */
    @Override
    public Training updateTraining(Long id, TrainingDto trainingDto) {
        Training existingTraining = trainingRepository.findById(id)
                .orElseThrow(() -> new TrainingNotFoundException(id));
        existingTraining.setDistance(trainingDto.getDistance());
        existingTraining.setAverageSpeed(trainingDto.getAverageSpeed());
        existingTraining.setEndTime(trainingDto.getEndTime());

        // Ustawienie pola DATE w formacie LocalDate (pierwszy dzień miesiąca)
        if (trainingDto.getEndTime() != null) {
            LocalDate localDate = trainingDto.getEndTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .withDayOfMonth(1);  // Ustawienie na pierwszy dzień miesiąca
            existingTraining.setDate(localDate);
        }

        return trainingRepository.save(existingTraining);
    }

    /**
     * Pobiera trening na podstawie jego identyfikatora.
     *
     * @param id identyfikator treningu
     * @return obiekt {@link Optional} zawierający trening lub pusty, jeśli nie znaleziono
     */
    @Override
    public Optional<Training> getTrainingById(Long id) {
        return trainingRepository.findById(id);
    }

    /**
     * Pobiera treningi użytkownika w określonym roku i miesiącu.
     *
     * @param userId identyfikator użytkownika
     * @param year   rok
     * @param month  miesiąc (1-12)
     * @return lista treningów użytkownika w podanym roku i miesiącu
     */
    @Override
    public List<Training> getTrainingsByUserIdAndMonth(Long userId, int year, int month) {
        return trainingRepository.findAll().stream()
                .filter(training -> training.getUser().getId().equals(userId))
                .filter(training -> {
                    Date startTime = training.getStartTime();
                    // Sprawdź, czy czas rozpoczęcia treningu mieści się w podanym roku i miesiącu
                    return startTime.getYear() == year - 1900 && startTime.getMonth() == month - 1;
                })
                .toList();
    }



//    @Override
//    public Optional<User> getTraining(final Long trainingId) {
//        throw new UnsupportedOperationException("Not finished yet");
//    }

}
