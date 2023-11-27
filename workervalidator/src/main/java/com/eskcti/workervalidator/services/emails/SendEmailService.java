package com.eskcti.workervalidator.services.emails;

import lombok.Builder;
import lombok.Getter;

public interface SendEmailService {
    void send(Message message);

    @Getter
    @Builder
    class Message {
        private String receiver;

        private String subject;

        private String body;
    }
}
