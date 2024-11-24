package com.capgemini.wsb.fitnesstracker.user.api;

/**
 * Klasa DTO (Data Transfer Object) reprezentująca uproszczone dane użytkownika.
 * Służy do przesyłania tylko podstawowych informacji o użytkowniku, takich jak identyfikator i adres email.
 */
public class UserSimpleDto {
    private Long id;
    private String email;

    /**
     * Konstruktor tworzący obiekt {@link UserSimpleDto} z identyfikatorem i adresem email.
     *
     * @param id identyfikator użytkownika
     * @param email adres email użytkownika
     */
    public UserSimpleDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    /**
     * Pobiera identyfikator użytkownika.
     *
     * @return identyfikator użytkownika
     */
    public Long getId() {
        return id;
    }

    /**
     * Ustawia identyfikator użytkownika.
     *
     * @param id identyfikator użytkownika do ustawienia
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Pobiera adres email użytkownika.
     *
     * @return adres email użytkownika
     */
    public String getEmail() {
        return email;
    }

    /**
     * Ustawia adres email użytkownika.
     *
     * @param email adres email użytkownika do ustawienia
     */
    public void setEmail(String email) {
        this.email = email;
    }


}
