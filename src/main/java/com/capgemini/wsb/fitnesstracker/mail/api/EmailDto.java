package com.capgemini.wsb.fitnesstracker.mail.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record EmailDto(
    @NotNull @Email String toAddress,
    @NotNull String subject,
    @NotNull String content) {

        // Konstruktor walidujący
    public EmailDto {
            if (toAddress == null || subject == null || content == null) {
                throw new IllegalArgumentException("Fields 'toAddress', 'subject' and 'content' cannot be null.");
            }
        }
    }
