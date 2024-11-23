package com.capgemini.wsb.fitnesstracker.statistics.api;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import jakarta.persistence.*;
import lombok.*;

/**
 * Reprezentuje statystyki związane z aktywnością fizyczną użytkownika.
 * Klasa ta jest mapowana na tabelę "statistics" w bazie danych.
 * Zawiera dane dotyczące liczby treningów, całkowitej odległości oraz spalonych kalorii.
 */
@Entity
@Table(name = "statistics")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Statistics {

    /**
     * Unikalny identyfikator statystyki.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Użytkownik, do którego należą te statystyki.
     * Relacja wielu do jednego, ponieważ jeden użytkownik może mieć wiele statystyk.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Całkowita liczba treningów użytkownika.
     * Pole to nie może być puste (nullable = false).
     */
    @Column(name = "total_trainings", nullable = false)
    private int totalTrainings;

    /**
     * Całkowita pokonana odległość przez użytkownika w ramach treningów.
     */
    @Column(name = "total_distance")
    private double totalDistance;

    /**
     * Całkowita liczba spalonych kalorii przez użytkownika podczas treningów.
     */
    @Column(name = "total_calories_burned")
    private int totalCaloriesBurned;

}