package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;




import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/trainings")
public class TrainingController {
    private final TrainingProvider trainingProvider;

    @Autowired
    public TrainingController(TrainingProvider trainingProvider) {
        this.trainingProvider = trainingProvider;
    }

//    @GetMapping
//    public ResponseEntity<List<Training>> getAllTrainings() {
//        return ResponseEntity.ok(trainingProvider.getAllTrainings());
//    }

    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAllTrainings() {
        List<TrainingDto> trainings = trainingProvider.getAllTrainings()
                .stream()
                .map(TrainingMapper::toDto)
                .toList();
        return ResponseEntity.ok(trainings);
    }



    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Training>> getTrainingsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(trainingProvider.getTrainingsByUserId(userId));
    }

//    @GetMapping("/completedAfter")
//    public ResponseEntity<List<Training>> getCompletedTrainingsAfter(@RequestParam Date date) {
//        return ResponseEntity.ok(trainingProvider.getCompletedTrainingsAfter(date));
//    }

    @GetMapping("/completedAfter")
    public ResponseEntity<List<Training>> getCompletedTrainingsAfter(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return ResponseEntity.ok(trainingProvider.getCompletedTrainingsAfter(date));
    }



//    @GetMapping("/activityType")
//    public ResponseEntity<List<Training>> getTrainingsByActivityType(@RequestParam ActivityType activityType) {
//        return ResponseEntity.ok(trainingProvider.getTrainingsByActivityType(activityType));
//    }


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


    @PostMapping
    public ResponseEntity<Training> createTraining(@RequestBody TrainingDto trainingDto) {
        return ResponseEntity.ok(trainingProvider.createTraining(trainingDto));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Training> updateTraining(@PathVariable Long id, @RequestBody TrainingDto trainingDto) {
        return ResponseEntity.ok(trainingProvider.updateTraining(id, trainingDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Training> getTrainingById(@PathVariable Long id) {
        return trainingProvider.getTrainingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
