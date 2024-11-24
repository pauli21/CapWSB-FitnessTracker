package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;
import com.capgemini.wsb.fitnesstracker.user.api.UserSimpleDto;

/**
 * Klasa pomocnicza (mapper) służąca do konwertowania obiektów {@link User} i {@link UserDto}.
 * Działa jako most między warstwą aplikacyjną a warstwą danych, umożliwiając łatwe mapowanie
 * między obiektami danych, które mogą być używane w różnych częściach aplikacji.
 */
@Component
class UserMapper {

    /**
     * Konwertuje obiekt {@link User} na obiekt {@link UserDto}.
     *
     * @param user obiekt użytkownika do konwersji
     * @return obiekt typu {@link UserDto}, który zawiera dane użytkownika w formie DTO
     */
    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                           user.getFirstName(),
                           user.getLastName(),
                           user.getBirthdate(),
                           user.getEmail());
    }

    /**
     * Konwertuje obiekt {@link UserDto} na obiekt {@link User}.
     *
     * @param userDto obiekt DTO użytkownika do konwersji
     * @return obiekt typu {@link User}, który zawiera pełne dane użytkownika
     */
    User toEntity(UserDto userDto) {
        return new User(
                        userDto.firstName(),
                        userDto.lastName(),
                        userDto.birthdate(),
                        userDto.email());
    }

    /**
     * Konwertuje obiekt {@link User} na obiekt {@link UserSimpleDto}, zawierający tylko uproszczone dane użytkownika.
     *
     * @param user obiekt użytkownika do konwersji
     * @return obiekt typu {@link UserSimpleDto}, który zawiera tylko podstawowe informacje użytkownika (id, email)
     */
    UserSimpleDto toSimpleDto(User user) {
        return new UserSimpleDto(user.getId(),
                                 user.getEmail());
    }

}
