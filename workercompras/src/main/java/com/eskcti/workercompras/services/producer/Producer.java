package com.eskcti.workercompras.services.producer;

import com.eskcti.workercompras.models.Card;
import com.eskcti.workercompras.models.Order;
import com.eskcti.workercompras.services.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class Producer {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;
    private final ObjectMapper mapper;
    private final CardService cartaoService;

    @SneakyThrows
    @PostMapping
    public void sendOrder(Order order) {
        order.setCard(Card.builder()
                .number(cartaoService.generateCard())
                .availableLimit(cartaoService.generateLimit())
                .build());

        rabbitTemplate.convertAndSend(queue.getName(), mapper.writeValueAsString(order));
        log.info("Pedido montado com sucesso Worker compras {}", order);
    }

}
