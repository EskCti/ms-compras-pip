package com.eskcti.workervalidator.services.emails;

import com.eskcti.workervalidator.models.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SendEmailService sendEmail;

    public void notifyCustomerPurchaseSuccessfully(Order order) {
        try {
            var message = SendEmailService.Message.builder()
                    .receiver(order.getEmail())
                    .subject("Compra aprovada")
                    .body("Este é um e-mail de confirmação de compra aprovada com sucesso. " +
                                    "\nObrigado por comprar com a gente!!")
                            .build();

            sendEmail.send(message);
            log.info("order email {}: {} ", order.getEmail(), order);
            log.info("Cliente notificado de aprovação com sucesso!!");

        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
