package com.capgemini.wsb.fitnesstracker.training;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import com.capgemini.wsb.fitnesstracker.user.api.UserSimpleDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Test
    void shouldReturnAllTrainings() {
        // Arrange
        List<Training> trainings = List.of(new Training());
        Mockito.when(trainingRepository.findAll()).thenReturn(trainings);

        // Act
        List<Training> result = trainingService.getAllTrainings();

        // Assert
        assertEquals(1, result.size());
        verify(trainingRepository).findAll();
    }

    @Test
    void shouldReturnTrainingsByUserId() {
        // Arrange
        Long userId = 1L;
        Training training = new Training();
        training.setUser(new com.capgemini.wsb.fitnesstracker.user.api.User());
        training.getUser().setId(userId);
        Mockito.when(trainingRepository.findAll()).thenReturn(List.of(training));

        // Act
        List<Training> result = trainingService.getTrainingsByUserId(userId);

        // Assert
        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).getUser().getId());
    }

    @Test
    void shouldCreateNewTraining() {
        // Arrange
        TrainingDto dto = new TrainingDto();
        dto.setDistance(10.5);
        Training training = new Training();
        Mockito.when(trainingRepository.save(Mockito.any(Training.class))).thenReturn(training);

        // Act
        Training result = trainingService.createTraining(dto);

        // Assert
        assertEquals(training, result);
        verify(trainingRepository).save(Mockito.any(Training.class));
    }

    @Test
    void shouldUpdateExistingTraining() {
        // Arrange
        Long trainingId = 1L;
        TrainingDto dto = new TrainingDto();
        dto.setDistance(15.0);
        Training existingTraining = new Training();
        existingTraining.setDistance(10.0);
        Mockito.when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(existingTraining));
        Mockito.when(trainingRepository.save(existingTraining)).thenReturn(existingTraining);

        // Act
        Training result = trainingService.updateTraining(trainingId, dto);

        // Assert
        assertEquals(15.0, result.getDistance());
        verify(trainingRepository).findById(trainingId);
        verify(trainingRepository).save(existingTraining);
    }
}
