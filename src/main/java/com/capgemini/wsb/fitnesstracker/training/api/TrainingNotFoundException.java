package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.exception.api.NotFoundException;

/**
 * Exception indicating that the {@link Training} was not found.
 */
@SuppressWarnings("squid:S110")
public class TrainingNotFoundException extends NotFoundException {

    /**
     * Prywatny konstruktor pozwalający na utworzenie wyjątku z niestandardową wiadomością.
     *
     * @param message wiadomość opisująca wyjątek
     */
    private TrainingNotFoundException(String message) {
        super(message);
    }

    /**
     * Publiczny konstruktor tworzący wyjątek z informacją o brakującym treningu na podstawie jego ID.
     *
     * @param id identyfikator treningu, który nie został znaleziony
     */
    public TrainingNotFoundException(Long id) {
        this("Training with ID=%s was not found".formatted(id));
    }

}
