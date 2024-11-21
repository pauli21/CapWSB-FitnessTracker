package com.capgemini.wsb.fitnesstracker.user.api;

public class UserSimpleDto {
    private Long id;
    private String email;

    public UserSimpleDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    // Gettery i settery
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
