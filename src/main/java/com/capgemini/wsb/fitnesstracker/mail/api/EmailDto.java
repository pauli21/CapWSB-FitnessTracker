package com.capgemini.wsb.fitnesstracker.mail.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
/**
 * Klasa reprezentująca dane e-mail do wysyłki.
 * Zawiera adres odbiorcy, temat oraz treść wiadomości.
 *
 * @param toAddress adres e-mail odbiorcy (nie może być pusty ani null).
 * @param subject temat wiadomości (nie może być pusty ani null).
 * @param content treść wiadomości e-mail (nie może być pusta ani null).
 */
public record EmailDto(
    @NotNull @Email String toAddress,
    @NotNull String subject,
    @NotNull String content) {

    /**
     * Konstruktor klasy EmailDto.
     * Waliduje, że żadne pole nie jest null, w przeciwnym razie rzuca wyjątek IllegalArgumentException.
     *
     * @param toAddress adres e-mail odbiorcy (nie może być null).
     * @param subject temat wiadomości (nie może być null).
     * @param content treść wiadomości e-mail (nie może być null).
     * @throws IllegalArgumentException gdy jedno z pól toAddress, subject lub content jest null.
     */
    public EmailDto {
            if (toAddress == null || subject == null || content == null) {
                throw new IllegalArgumentException("Fields 'toAddress', 'subject' and 'content' cannot be null.");
            }
        }
    }
