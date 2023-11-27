package com.eskcti.workercompras.configs;

import com.eskcti.workercompras.services.emails.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private JavaMailSender mailSender;

    @Bean
    public SendEmailService sendEmailService() {
        // Acho melhor usar switch aqui do que if/else if
        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeSendEmailService();
            case SENDGRID:
                return new SendGridSmtpSendEmailService(mailSender, emailProperties);
            case GMAIL:
                return new GmailSmtpSendEmailService(mailSender, emailProperties);
            default:
                return null;
        }
    }
}
