package com.capgemini.wsb.fitnesstracker.training;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingController;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testy jednostkowe kontrolera {@link TrainingController}.
 * Sprawdzają poprawność działania endpointów kontrolera poprzez symulację
 * zapytań HTTP oraz weryfikację odpowiedzi.
 */
@WebMvcTest(TrainingController.class)
class TrainingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainingServiceImpl trainingService;

    /**
     * Testuje endpoint do pobierania wszystkich treningów.
     * Sprawdza, czy serwer poprawnie zwraca status 200 oraz czy odpowiedź jest tablicą.
     *
     * @throws Exception Jeśli wystąpi błąd w trakcie wykonywania zapytania.
     */
    @Test
    void shouldReturnAllTrainings() throws Exception {
        // Arrange
        Mockito.when(trainingService.getAllTrainings()).thenReturn(List.of(new Training()));

        // Act & Assert
        mockMvc.perform(get("/trainings").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    /**
     * Testuje endpoint do pobierania treningów na podstawie ID użytkownika.
     * Sprawdza, czy serwer zwraca poprawny status oraz czy odpowiedź to tablica.
     *
     * @throws Exception Jeśli wystąpi błąd w trakcie wykonywania zapytania.
     */
    @Test
    void shouldReturnTrainingsByUserId() throws Exception {
        // Arrange
        Long userId = 1L;
        Mockito.when(trainingService.getTrainingsByUserId(userId)).thenReturn(List.of(new Training()));

        // Act & Assert
        mockMvc.perform(get("/trainings/user/{userId}", userId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    /**
     * Testuje endpoint do tworzenia nowego treningu.
     * Sprawdza, czy serwer zwraca status 200 po wysłaniu zapytania POST z danymi treningu.
     *
     * @throws Exception Jeśli wystąpi błąd w trakcie wykonywania zapytania.
     */
    @Test
    void shouldCreateNewTraining() throws Exception {
        // Arrange
        TrainingDto dto = new TrainingDto();
        dto.setDistance(10.5);
        String requestBody = """
                {
                    "distance": 10.5,
                    "activityType": "RUNNING"
                }
                """;
        Mockito.when(trainingService.createTraining(Mockito.any())).thenReturn(new Training());

        // Act & Assert
        mockMvc.perform(post("/trainings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    /**
     * Testuje endpoint do aktualizacji treningu.
     * Sprawdza, czy serwer zwraca status 200 po wysłaniu zapytania PUT z danymi aktualizacji.
     *
     * @throws Exception Jeśli wystąpi błąd w trakcie wykonywania zapytania.
     */
    @Test
    void shouldUpdateTraining() throws Exception {
        // Arrange
        Long trainingId = 1L;
        String requestBody = """
                {
                    "distance": 20.0
                }
                """;
        Mockito.when(trainingService.updateTraining(Mockito.eq(trainingId), Mockito.any())).thenReturn(new Training());

        // Act & Assert
        mockMvc.perform(put("/trainings/{id}", trainingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }
}
