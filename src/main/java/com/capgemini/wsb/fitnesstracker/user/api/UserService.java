package com.capgemini.wsb.fitnesstracker.user.api;

import java.util.List;

/**
 * Interfejs (API) do operacji modyfikujących encje {@link User} poprzez API.
 * Klasy implementujące ten interfejs są odpowiedzialne za wykonywanie zmian w bazie danych,
 * zarówno w ramach istniejącej transakcji, jak i tworzenie nowej transakcji, gdy jest to wymagane.
 */
public interface UserService {
    /**
     * Tworzy nowego użytkownika w systemie.
     * Wykorzystuje dane użytkownika przekazane w obiekcie {@link User}.
     *
     * @param user obiekt {@link User} zawierający dane użytkownika do utworzenia
     * @return utworzony obiekt {@link User} z nadanym identyfikatorem
     */
    User createUser(User user);

    /**
     * Usuwa użytkownika z systemu na podstawie jego identyfikatora.
     * Jeśli użytkownik o podanym identyfikatorze nie istnieje, może zostać zgłoszony wyjątek.
     *
     * @param id identyfikator użytkownika, który ma zostać usunięty
     */
    void deleteUser(Long id);

    /**
     * Aktualizuje dane użytkownika na podstawie jego identyfikatora.
     * Nowe dane użytkownika są przekazywane w obiekcie {@link User}.
     *
     * @param id identyfikator użytkownika, którego dane mają zostać zaktualizowane
     * @param user obiekt {@link User} zawierający nowe dane użytkownika
     * @return zaktualizowany obiekt {@link User}
     */
    User updateUser(Long id, User user);


}
