package com.eskcti.workervalidator.services.emails;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("workervalidator.email")
public class EmailProperties {
    private String sender;

    private String sendgrid;

    private String gmailUser;

    private String gmailPassword;

    private Implementation impl = Implementation.FAKE;

    public enum Implementation {
        SENDGRID, GMAIL, FAKE
    }
}
