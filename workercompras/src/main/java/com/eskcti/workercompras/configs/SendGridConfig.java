package com.eskcti.workercompras.configs;

import com.eskcti.workercompras.services.emails.EmailProperties;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SendGridConfig {

    @Autowired
    private EmailProperties properties;

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure().directory("src/.env").load();
    }

    @Bean
    public boolean configPropertiesWithEnv(Dotenv dotenv) {
        properties.setSendgrid(dotenv.get("SENDGRID_API_KEY"));
        properties.setGmailUser(dotenv().get("GMAIL_USER"));
        properties.setGmailPassword(dotenv().get("GMAIL_PASS"));
        return true;
    }
}
