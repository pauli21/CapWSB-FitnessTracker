package com.capgemini.wsb.fitnesstracker.user.api;

import java.util.List;
import java.util.Optional;

/**
 * Interfejs dostępu do danych użytkowników w systemie.
 * Zapewnia metody do pobierania użytkowników na podstawie różnych kryteriów,
 * takich jak identyfikator, email, oraz metodę do pobrania wszystkich użytkowników.
 */
public interface UserProvider {

    /**
     * Pobiera użytkownika na podstawie jego identyfikatora.
     * Jeśli użytkownik o podanym identyfikatorze nie zostanie znaleziony,
     * metoda zwróci {@link Optional#empty()}.
     *
     * @param userId identyfikator użytkownika, którego dane mają zostać pobrane
     * @return {@link Optional} zawierający użytkownika, lub {@link Optional#empty()} jeśli użytkownik nie został znaleziony
     */
    Optional<User> getUser(Long userId);

    /**
     * Pobiera użytkownika na podstawie jego adresu email.
     * Jeśli użytkownik o podanym emailu nie zostanie znaleziony,
     * metoda zwróci {@link Optional#empty()}.
     *
     * @param email adres email użytkownika, którego dane mają zostać pobrane
     * @return {@link Optional} zawierający użytkownika, lub {@link Optional#empty()} jeśli użytkownik nie został znaleziony
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Pobiera wszystkich użytkowników z systemu.
     *
     * @return lista wszystkich użytkowników
     */
    List<User> findAllUsers();

    /**
     * Pobiera użytkowników, których email zawiera określoną część.
     * Wynikiem będzie lista użytkowników, których adresy email zawierają podaną część ciągu.
     *
     * @param emailPart część adresu email, która ma być wyszukiwana w emailu użytkowników
     * @return lista obiektów {@link UserSimpleDto} pasujących do wyszukiwanego fragmentu emaila
     */
    List<UserSimpleDto> findUsersByEmail(String emailPart);

    /**
     * Pobiera użytkowników, którzy są starsi niż podany wiek.
     *
     * @param age wiek, który będzie punktem odniesienia do wyszukiwania użytkowników starszych niż ten wiek
     * @return lista obiektów {@link UserSimpleDto} użytkowników starszych niż podany wiek
     */
    List<UserSimpleDto> findUsersOlderThan(int age);

}
