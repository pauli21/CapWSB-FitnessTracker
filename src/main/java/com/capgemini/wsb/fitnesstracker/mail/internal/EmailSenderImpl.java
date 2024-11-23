package com.capgemini.wsb.fitnesstracker.mail.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Implementacja interfejsu EmailSender.
 * Odpowiada za wysyłanie wiadomości e-mail przy użyciu JavaMailSender.
 */
@Service
public class EmailSenderImpl implements EmailSender {

    private static final Logger logger = LoggerFactory.getLogger(EmailSenderImpl.class);
    private final JavaMailSender javaMailSender;

    /**
     * Konstruktor do wstrzykiwania zależności.
     *
     * @param javaMailSender obiekt umożliwiający wysyłanie wiadomości e-mail.
     */
    @Autowired
    public EmailSenderImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Metoda do wysyłania wiadomości e-mail.
     * Tworzy wiadomość MIME, ustawia jej właściwości (adres odbiorcy, temat, treść)
     * oraz wysyła ją za pomocą JavaMailSender.
     *
     * @param emailDto obiekt zawierający dane e-maila, takie jak adres odbiorcy,
     *                 temat oraz treść wiadomości.
     */
    @Override
    public void send(EmailDto emailDto) {
        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(emailDto.toAddress());
            helper.setSubject(emailDto.subject());
            helper.setText(emailDto.content());

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("Błąd podczas wysyłania e-maila: ", e); // Lepsza obsługa błędów
        }
    }
}

