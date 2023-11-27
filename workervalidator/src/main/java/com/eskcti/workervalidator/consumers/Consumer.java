package com.eskcti.workervalidator.consumers;

import com.eskcti.workervalidator.models.Order;
import com.eskcti.workervalidator.services.ValidatorService;
import com.eskcti.workervalidator.services.exceptions.InsufficientFundsException;
import com.eskcti.workervalidator.services.exceptions.LimitUnavailableException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class Consumer {

    private final ObjectMapper objectMapper;

    private final ValidatorService validatorService;

    @RabbitListener(queues = {"${queue.name}"})
    public void consumer(@Payload Message message) {
        try {
            Order order = objectMapper.readValue(message.getBody(), Order.class);
            log.info("Pedido recebido no workervalidator: {}", order);

            try {
                validatorService.validateOrder(order);
            } catch (LimitUnavailableException | InsufficientFundsException exception) {
                exception.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Erro ao converter Message para objeto Order: " + e.getMessage());
        }
    }
}
