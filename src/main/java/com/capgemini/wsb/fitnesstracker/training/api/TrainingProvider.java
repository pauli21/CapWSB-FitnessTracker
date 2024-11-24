package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Interfejs dostarczający metody do zarządzania obiektami typu {@link Training}.
 */
public interface TrainingProvider {

    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     //     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */


    /**
     * Pobiera wszystkie treningi.
     *
     * @return lista wszystkich treningów
     */
    List<Training> getAllTrainings();

    /**
     * Pobiera wszystkie treningi przypisane do użytkownika o podanym identyfikatorze.
     *
     * @param userId identyfikator użytkownika
     * @return lista treningów przypisanych do użytkownika
     */
    List<Training> getTrainingsByUserId(Long userId);

    /**
     * Pobiera listę ukończonych treningów po podanej dacie.
     *
     * @param date data, po której mają zostać wyszukane ukończone treningi
     * @return lista ukończonych treningów po podanej dacie
     */
    List<Training> getCompletedTrainingsAfter(Date date);

    /**
     * Pobiera listę treningów dla określonego typu aktywności.
     *
     * @param activityType typ aktywności treningu
     * @return lista treningów o określonym typie aktywności
     */
    List<Training> getTrainingsByActivityType(ActivityType activityType);

    /**
     * Tworzy nowy trening na podstawie przekazanego obiektu DTO.
     *
     * @param trainingDto dane treningu do utworzenia
     * @return utworzony obiekt treningu
     */
    Training createTraining(TrainingDto trainingDto);

    /**
     * Aktualizuje istniejący trening na podstawie przekazanego identyfikatora i danych DTO.
     *
     * @param id identyfikator treningu do zaktualizowania
     * @param trainingDto nowe dane treningu
     * @return zaktualizowany obiekt treningu
     */
    Training updateTraining(Long id, TrainingDto trainingDto);

    /**
     * Pobiera trening na podstawie jego identyfikatora.
     *
     * @param id identyfikator treningu
     * @return obiekt {@link Optional} zawierający znaleziony trening lub pusty, jeśli trening nie istnieje
     */
    Optional<Training> getTrainingById(Long id);

    /**
     * Pobiera listę treningów przypisanych do użytkownika w określonym miesiącu i roku.
     *
     * @param userId identyfikator użytkownika
     * @param month  miesiąc (1-12)
     * @param year   rok
     * @return lista treningów użytkownika w podanym miesiącu i roku
     */
    List<Training> getTrainingsByUserIdAndMonth(Long userId, int month, int year);



}
