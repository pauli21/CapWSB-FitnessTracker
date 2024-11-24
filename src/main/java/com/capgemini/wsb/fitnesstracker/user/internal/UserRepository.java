package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

/**
 * Repozytorium użytkowników, które rozszerza {@link JpaRepository} i zawiera niestandardową metodę
 * wyszukiwania użytkowników na podstawie ich adresu email.
 *
 * Używa Spring Data JPA do operacji na bazie danych, ale także dodaje funkcjonalność wyszukiwania
 * użytkowników po dokładnym dopasowaniu emaila.
 */
@Repository
interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Wyszukuje użytkownika po jego adresie email.
     * Metoda ta wykonuje dopasowanie dokładne, czyli szuka użytkownika, którego email dokładnie
     * odpowiada podanemu w argumencie.
     *
     * @param email email użytkownika do wyszukania
     * @return {@link Optional} zawierający znalezionego użytkownika, lub {@link Optional#empty()}
     *         jeśli żaden użytkownik nie pasuje do podanego adresu email
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                        .filter(user -> Objects.equals(user.getEmail(), email))
                        .findFirst();
    }

}
