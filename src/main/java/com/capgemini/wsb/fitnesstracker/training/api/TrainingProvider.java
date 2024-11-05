package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface TrainingProvider {

    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
//     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
//    Optional<User> getTraining(Long trainingId);
    List<Training> getAllTrainings();

    List<Training> getTrainingsByUserId(Long userId);

    List<Training> getCompletedTrainingsAfter(Date date);

    List<Training> getTrainingsByActivityType(ActivityType activityType);

    Training createTraining(TrainingDto trainingDto);

    Training updateTraining(Long id, TrainingDto trainingDto);

    Optional<Training> getTrainingById(Long id);


}
