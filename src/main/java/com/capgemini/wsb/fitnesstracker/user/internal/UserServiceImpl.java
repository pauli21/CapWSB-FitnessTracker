package com.capgemini.wsb.fitnesstracker.user.internal;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.api.UserSimpleDto;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * Implementacja serwisu użytkowników, który obsługuje operacje na użytkownikach,
 * takie jak tworzenie, aktualizacja, usuwanie oraz wyszukiwanie użytkowników.
 */
@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Tworzy nowego użytkownika w systemie.
     * Sprawdza, czy użytkownik ma już przypisane ID (co oznacza, że istnieje w bazie danych).
     * Jeśli tak, rzuca wyjątek.
     *
     * @param user Obiekt użytkownika, który ma zostać zapisany w systemie.
     * @return Zapisany obiekt użytkownika.
     * @throws IllegalArgumentException Jeśli użytkownik ma przypisane ID (czyli próbuje być zaktualizowany, a nie tworzony).
     */
    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
        // return userRepository.saveAll(user);
    }

    /**
     * Usuwa użytkownika z systemu na podstawie jego ID.
     *
     * @param id ID użytkownika do usunięcia.
     */
    @Override
    public void deleteUser(Long id) {
        log.info("Deleting User with ID {}", id);
        userRepository.deleteById(id);
    }

    /**
     * Wyszukuje użytkowników na podstawie fragmentu ich adresu e-mail.
     * Wyszukiwanie jest nieczułe na wielkość liter.
     *
     * @param emailPart Fragment adresu e-mail do wyszukania.
     * @return Lista {@link UserSimpleDto} z danymi użytkowników, którzy spełniają kryterium wyszukiwania.
     */
    @Override
    public List<UserSimpleDto> findUsersByEmail(String emailPart) {
        return userRepository.findAll().stream()
                             .filter(user -> user.getEmail().toLowerCase().contains(emailPart.toLowerCase()))
                             .map(userMapper::toSimpleDto)
                             .toList();
    }

    /**
     * Znajduje użytkowników, którzy są starsi niż podany wiek.
     *
     * @param age Wiek graniczny.
     * @return Lista {@link UserSimpleDto} z danymi użytkowników, którzy są starsi niż określony wiek.
     */
    @Override
    public List<UserSimpleDto> findUsersOlderThan(int age) {
        LocalDate cutoffDate = LocalDate.now().minusYears(age);
        return userRepository.findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(cutoffDate))
                .map(userMapper::toSimpleDto)
                .toList();
    }


    /**
     * Aktualizuje dane istniejącego użytkownika.
     *
     * @param id   ID użytkownika do aktualizacji.
     * @param user Obiekt użytkownika z nowymi danymi.
     * @return Zaktualizowany obiekt użytkownika.
     * @throws UserNotFoundException Jeśli użytkownik o podanym ID nie istnieje w systemie.
     */
    @Override
    public User updateUser(Long id, User user) {
        log.info("Updating User with ID {}", id);
        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setBirthdate(user.getBirthdate());
        existingUser.setEmail(user.getEmail());

        return userRepository.save(existingUser);
    }



    /**
     * Pobiera użytkownika na podstawie jego ID.
     *
     * @param userId ID użytkownika, którego dane mają zostać pobrane.
     * @return {@link Optional} zawierający użytkownika lub {@link Optional#empty()} jeśli użytkownik nie istnieje.
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Pobiera użytkownika na podstawie jego adresu e-mail.
     *
     * @param email Adres e-mail użytkownika, którego dane mają zostać pobrane.
     * @return {@link Optional} zawierający użytkownika lub {@link Optional#empty()} jeśli użytkownik nie istnieje.
     */
    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Pobiera wszystkich użytkowników z systemu.
     *
     * @return Lista wszystkich użytkowników.
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

}