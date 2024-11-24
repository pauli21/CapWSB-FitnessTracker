package com.capgemini.wsb.fitnesstracker.user.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Rekord reprezentujący dane użytkownika w formacie transferu (DTO).
 * Służy do przesyłania informacji o użytkowniku, takich jak identyfikator, imię, nazwisko,
 * data urodzenia oraz adres email.
 *
 * Zastosowanie @JsonFormat pozwala na określenie formatu daty przy serializacji i deserializacji.
 */
record UserDto(@Nullable Long Id, String firstName, String lastName,
               @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
               String email) {

}
