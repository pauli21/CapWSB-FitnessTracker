package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.capgemini.wsb.fitnesstracker.user.api.UserSimpleDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    /**
     * Pobiera listę wszystkich użytkowników z podstawowymi informacjami (ID i nazwy).
     *
     * @return Lista {@link UserDto} zawierająca dane użytkowników.
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    /**
     * Pobiera szczegóły konkretnego użytkownika po jego ID.
     *
     * @param id ID użytkownika do pobrania.
     * @return Obiekt {@link UserDto} zawierający szczegóły użytkownika.
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id){
        return userMapper.toDto(userService.getUser(id).orElseThrow());
    }

    /**
     * Pobiera uproszczoną listę wszystkich użytkowników (tylko ID i e-mail).
     *
     * @return Lista {@link UserSimpleDto} zawierająca podstawowe informacje o użytkownikach.
     */
    @GetMapping("/simple")
    public List<UserSimpleDto> getAllUsersSimple(){
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toSimpleDto)
                          .toList();
    }

    /**
     * Dodaje nowego użytkownika do systemu.
     *
     * @param userDto Dane użytkownika do dodania.
     * @return ResponseEntity zawierająca szczegóły utworzonego użytkownika oraz status 201 (Utworzono).
     */
    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        log.info("User with e-mail: {} passed to the request", userDto.email());

        User newUser = userMapper.toEntity(userDto);
        User createdUser = userService.createUser(newUser);

        // Mapowanie utworzonego użytkownika z powrotem na DTO przed zwróceniem
        UserDto createdUserDto = userMapper.toDto(createdUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

    /**
     * Usuwa konkretnego użytkownika z systemu po jego ID.
     *
     * @param id ID użytkownika do usunięcia.
     * @return ResponseEntity ze statusem 204 (Brak treści), jeśli usunięcie zakończyło się sukcesem.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Wyszukuje użytkowników po fragmencie ich adresu e-mail (niezależnie od wielkości liter).
     *
     * @param emailPart Fragment adresu e-mail do wyszukania.
     * @return Lista {@link UserSimpleDto} zawierająca ID i e-maile pasujących użytkowników.
     */
    @GetMapping("/search/email")
    public List<UserSimpleDto> findUsersByEmail(@RequestParam String emailPart) {
        return userService.findUsersByEmail(emailPart);
    }

    /**
     * Znajduje użytkowników starszych niż określony wiek.
     *
     * @param age Wiek graniczny.
     * @return Lista {@link UserSimpleDto} zawierająca ID i e-maile pasujących użytkowników.
     */
    @GetMapping("/search/age")
    public List<UserSimpleDto> findUsersOlderThan(@RequestParam int age) {
        return userService.findUsersOlderThan(age);
    }

    /**
     * Aktualizuje informacje o istniejącym użytkowniku.
     *
     * @param id      ID użytkownika do aktualizacji.
     * @param userDto Nowe dane użytkownika.
     * @return ResponseEntity zawierająca szczegóły zaktualizowanego użytkownika.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUser(id, userMapper.toEntity(userDto));
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }




}

