package com.eskcti.mscompra.services;

import com.eskcti.mscompra.models.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

public class DataMock {
    public Order getOrder() {
        return Order.builder()
                .name("João da Silva")
                .valueOrder(BigDecimal.TEN)
                .cpfCustomer("123456789012")
                .email("joaodasilva@gmail.com")
                .id(1L)
                .product(1L)
                .purchaseDate(new Date())
                .zipCode("12345678")
                .build();
    }
}
