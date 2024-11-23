package com.capgemini.wsb.fitnesstracker.mail.internal;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


/**
 * Konfiguracja dla usługi e-mail.
 * Definiuje bean dla JavaMailSender, który będzie używany do wysyłania wiadomości e-mail.
 * Ustawia hosta SMTP, port oraz dane logowania (jeśli wymagane).
 */
@Configuration
@EnableConfigurationProperties(MailProperties.class)
class MailConfig {
    /**
     * Tworzy i konfiguruje obiekt JavaMailSender, który umożliwia wysyłanie e-maili.
     *
     * @return skonfigurowany obiekt JavaMailSender, który może być użyty do wysyłania wiadomości.
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.wp.pl");
        mailSender.setPort(587);
        mailSender.setUsername("paulinakamilawsbmerito@wp.pl");
        mailSender.setPassword("PaulinaKamilaWSB#");


        return mailSender;
    }

}

