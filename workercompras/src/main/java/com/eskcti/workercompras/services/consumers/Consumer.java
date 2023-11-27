package com.eskcti.workercompras.services.consumers;

import com.eskcti.workercompras.models.Endereco;
import com.eskcti.workercompras.models.Order;
import com.eskcti.workercompras.services.CepService;
import com.eskcti.workercompras.services.emails.EmailService;
import com.eskcti.workercompras.services.exceptions.EnderecoNotFoundException;
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
    private final EmailService emailService;
    private final CepService cepService;

    @RabbitListener(queues = {"${queue.name}"})
    public void consumer(@Payload Message message) {
        try {
            Order order = objectMapper.readValue(message.getBody(), Order.class);
            log.info("Pedido recebido: {}", order);
            Endereco endereco = cepService.buscarCep(order.getZipCode());
            if (endereco.getLogradouro() == null) {
                throw new EnderecoNotFoundException("Endereço não encontrado");
            }
            order.setEndereco(endereco);
            emailService.notificarClient(order);
        } catch (Exception e) {
            System.err.println("Erro ao converter Message para objeto Order: " + e.getMessage());
        }
    }
}
