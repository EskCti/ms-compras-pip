package com.eskcti.mscompra.services;

import com.eskcti.mscompra.models.Order;
import com.eskcti.mscompra.repositories.OrderRepository;
import com.eskcti.mscompra.services.exception.EntityNotFoundException;
import com.eskcti.mscompra.services.rabbitmq.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final Producer producer;

    public Order save(Order order) {
        order = orderRepository.save(order);
        producer.sendOrder(order);
        return order;
    }

    public Order findOrFailById(long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("O pedido de id: " + id + " nao existe na base de dados!"));
    }

    public void deleteById(long id) {
        orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("O pedido de id: " + id + " nao existe na base de dados!"));

        orderRepository.deleteById(id);
    }
}
