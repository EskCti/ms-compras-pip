package com.eskcti.workercompras.services.emails;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("workercompras.email")
public class EmailProperties {
    @NotNull
    private String sender;

    private String sendgrid;

    private String gmailUser;

    private String gmailPassword;

    private Implementation impl = Implementation.FAKE;

    public enum Implementation {
        SENDGRID, GMAIL, FAKE
    }
}
