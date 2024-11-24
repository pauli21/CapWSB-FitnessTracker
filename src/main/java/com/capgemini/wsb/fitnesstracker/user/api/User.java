package com.capgemini.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Klasa reprezentująca użytkownika w systemie.
 * Zawiera dane osobowe użytkownika, takie jak imię, nazwisko, data urodzenia oraz email.
 */
@Entity
@Table(name = "users")
@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PUBLIC)

@ToString
public class User {
    /**
     * Unikalny identyfikator użytkownika.
     * Jest generowany automatycznie w bazie danych.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Konstruktor do tworzenia obiektu użytkownika z danymi.
     *
     * @param firstName imię użytkownika
     * @param lastName nazwisko użytkownika
     * @param birthdate data urodzenia użytkownika
     * @param email adres email użytkownika
     */
    public User(
            final String firstName,
            final String lastName,
            final LocalDate birthdate,
            final String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
    }

    /**
     * Ustawia imię użytkownika.
     *
     * @param firstName imię użytkownika
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Ustawia nazwisko użytkownika.
     *
     * @param lastName nazwisko użytkownika
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Ustawia datę urodzenia użytkownika.
     *
     * @param birthdate data urodzenia użytkownika
     */
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * Ustawia identyfikator użytkownika.
     *
     * @param id identyfikator użytkownika
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Ustawia adres email użytkownika.
     *
     * @param email adres email użytkownika
     */
    public void setEmail(String email) {
        this.email = email;
    }


}

