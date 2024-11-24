package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.exception.api.NotFoundException;

/**
 * Exception indicating that the {@link User} was not found.
 */
/**
 * Wyjątek wskazujący, że użytkownik o podanym identyfikatorze nie został znaleziony.
 * Rozszerza klasę {@link NotFoundException}, która jest używana do zgłaszania błędów,
 * gdy zasób nie zostaje odnaleziony w systemie.
 */
@SuppressWarnings("squid:S110")
public class UserNotFoundException extends NotFoundException {
    /**
     * Konstruktor prywatny do tworzenia wyjątku z niestandardową wiadomością.
     * Używany wewnętrznie do tworzenia wyjątków o szczególnych komunikatach.
     *
     * @param message szczegółowy komunikat błędu
     */
    private UserNotFoundException(String message) {
        super(message);
    }
    /**
     * Konstruktor wyjątku, który generuje komunikat błędu zawierający identyfikator użytkownika.
     * Używany, gdy użytkownik o określonym identyfikatorze nie został znaleziony w systemie.
     *
     * @param id identyfikator użytkownika, który nie został znaleziony
     */
    public UserNotFoundException(Long id) {
        this("User with ID=%s was not found".formatted(id));
    }

}
