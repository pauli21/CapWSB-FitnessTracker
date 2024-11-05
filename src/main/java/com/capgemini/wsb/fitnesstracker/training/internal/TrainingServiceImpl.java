package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Date;
import java.util.List;

// TODO: Provide Impl
@Service
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getTrainingsByUserId(Long userId) {
        return trainingRepository.findAll().stream()
                .filter(training -> training.getUser().getId().equals(userId))
                .toList();
    }

    @Override
    public List<Training> getCompletedTrainingsAfter(Date date) {
        return trainingRepository.findAll().stream()
                .filter(training -> training.getEndTime().after(date))
                .toList();
    }

    @Override
    public List<Training> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findAll().stream()
                .filter(training -> training.getActivityType() == activityType)
                .toList();
    }

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

    @Override
    public Training updateTraining(Long id, TrainingDto trainingDto) {
        Training existingTraining = trainingRepository.findById(id)
                .orElseThrow(() -> new TrainingNotFoundException(id));
        existingTraining.setDistance(trainingDto.getDistance());
        existingTraining.setAverageSpeed(trainingDto.getAverageSpeed());
        return trainingRepository.save(existingTraining);
    }

    @Override
    public Optional<Training> getTrainingById(Long id) {
        return trainingRepository.findById(id);
    }


//    @Override
//    public Optional<User> getTraining(final Long trainingId) {
//        throw new UnsupportedOperationException("Not finished yet");
//    }

}
