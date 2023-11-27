package com.eskcti.workercompras.services.emails;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
public class FakeSendEmailService implements SendEmailService{
    @Override
    public void send(Message message) {
        log.info("[FAKE E-MAIL] Para: {}\n{}", message.getReceiver(), message.getBody());
    }
}
