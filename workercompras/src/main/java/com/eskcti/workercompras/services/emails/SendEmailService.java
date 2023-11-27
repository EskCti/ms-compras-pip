package com.eskcti.workercompras.services.emails;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.Set;

public interface SendEmailService {
    void send(Message message);

    @Getter
    @Builder
    class Message {
        private String receiver;

        @NotNull
        private String subject;

        @NotNull
        private String body;
    }
}
