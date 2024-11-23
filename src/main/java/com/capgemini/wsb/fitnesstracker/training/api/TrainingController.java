package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;




import java.util.Date;
import java.util.List;

/**
 * Kontroler obsługujący zapytania HTTP związane z treningami.
 */
@RestController
@RequestMapping("/trainings")
public class TrainingController {
    private final TrainingProvider trainingProvider;

    /**
     * Konstruktor kontrolera przyjmujący zależność od TrainingProvider.
     *
     * @param trainingProvider Dostawca usług treningowych.
     */
    @Autowired
    public TrainingController(TrainingProvider trainingProvider) {
        this.trainingProvider = trainingProvider;
    }

    /**
     * Pobiera wszystkie dostępne treningi.
     *
     * @return Lista wszystkich treningów w formacie DTO.
     */
    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAllTrainings() {
        List<TrainingDto> trainings = trainingProvider.getAllTrainings()
                .stream()
                .map(TrainingMapper::toDto)
                .toList();
        return ResponseEntity.ok(trainings);
    }


    /**
     * Pobiera wszystkie treningi dla użytkownika na podstawie jego identyfikatora.
     *
     * @param userId Identyfikator użytkownika.
     * @return Lista treningów użytkownika.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Training>> getTrainingsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(trainingProvider.getTrainingsByUserId(userId));
    }

    /**
     * Pobiera wszystkie treningi zakończone po określonej dacie.
     *
     * @param date Data po której mają być pobrane treningi.
     * @return Lista treningów zakończonych po zadanej dacie.
     */
    @GetMapping("/completedAfter")
    public ResponseEntity<List<Training>> getCompletedTrainingsAfter(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return ResponseEntity.ok(trainingProvider.getCompletedTrainingsAfter(date));
    }



    /**
     * Pobiera wszystkie treningi określonego typu aktywności.
     *
     * @param activityType Typ aktywności (np. bieganie, jazda na rowerze).
     * @return Lista treningów dla podanego typu aktywności.
     */
    @GetMapping("/activityType")
    public ResponseEntity<List<Training>> getTrainingsByActivityType(
            @RequestParam String activityType) {
        try {
            ActivityType type = ActivityType.valueOf(activityType.toUpperCase());
            return ResponseEntity.ok(trainingProvider.getTrainingsByActivityType(type));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(List.of());
        }
    }

    /**
     * Tworzy nowy trening na podstawie danych dostarczonych w obiekcie TrainingDto.
     *
     * @param trainingDto Obiekt DTO zawierający dane nowego treningu.
     * @return Stworzony trening.
     */
    @PostMapping
    public ResponseEntity<Training> createTraining(@RequestBody TrainingDto trainingDto) {
        return ResponseEntity.ok(trainingProvider.createTraining(trainingDto));
    }

    /**
     * Aktualizuje istniejący trening na podstawie jego identyfikatora.
     *
     * @param id Identyfikator treningu.
     * @param trainingDto Zaktualizowane dane treningu.
     * @return Zaktualizowany trening.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Training> updateTraining(@PathVariable Long id, @RequestBody TrainingDto trainingDto) {
        return ResponseEntity.ok(trainingProvider.updateTraining(id, trainingDto));
    }
    /**
     * Pobiera szczegóły konkretnego treningu na podstawie jego identyfikatora.
     *
     * @param id Identyfikator treningu.
     * @return Szczegóły treningu.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Training> getTrainingById(@PathVariable Long id) {
        return trainingProvider.getTrainingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }




}
