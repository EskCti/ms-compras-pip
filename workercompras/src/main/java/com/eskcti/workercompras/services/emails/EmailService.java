package com.eskcti.workercompras.services.emails;

import com.eskcti.workercompras.models.Order;
import com.eskcti.workercompras.services.producer.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SendEmailService sendEmail;
    private final Producer producer;

    public void notificarClient(Order order) {
        try {
            var message = SendEmailService.Message.builder()
                    .receiver(order.getEmail())
                    .subject("Compra recebida")
                    .body("Este é um e-mail de confirmação de compra recebida. " +
                                    "Agora vamos aprovar sua compra e brevemente você receberá um novo e-mail de confirmação." +
                                    "\nObrigado por comprar com a gente!!")
                            .build();

            sendEmail.send(message);
            log.info("order email {}: {} ", order.getEmail(), order);
            log.info("Cliente notificado com sucesso!!");

            producer.sendOrder(order);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
