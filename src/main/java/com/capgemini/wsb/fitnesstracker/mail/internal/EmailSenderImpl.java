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

@Service
public class EmailSenderImpl implements EmailSender {

    private static final Logger logger = LoggerFactory.getLogger(EmailSenderImpl.class);
    private final JavaMailSender javaMailSender;

    // Konstruktor do wstrzykiwania zależności
    @Autowired
    public EmailSenderImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(EmailDto emailDto) {
        try {
            // Tworzymy wiadomość MIME
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Ustawiamy parametry e-maila
            helper.setTo(emailDto.toAddress());
            helper.setSubject(emailDto.subject());
            helper.setText(emailDto.content());

            // Wysyłamy e-mail
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("Błąd podczas wysyłania e-maila: ", e); // Lepsza obsługa błędów
        }
    }
}

