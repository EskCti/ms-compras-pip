package com.eskcti.workervalidator.services.emails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeSendEmailService implements SendEmailService{
    @Override
    public void send(Message message) {
        log.info("[FAKE E-MAIL] Para: {}\n{}", message.getReceiver(), message.getBody());
    }
}
