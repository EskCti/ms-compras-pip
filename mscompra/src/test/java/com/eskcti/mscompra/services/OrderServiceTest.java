package com.eskcti.mscompra.services;

import com.eskcti.mscompra.models.Order;
import com.eskcti.mscompra.repositories.OrderRepository;
import com.eskcti.mscompra.services.exception.BusinessException;
import com.eskcti.mscompra.services.rabbitmq.Producer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Producer producer;

    private DataMock dataMock = new DataMock();

    @DisplayName("Save order with success")
    @Test
    void shouldSaveAnOrderWithSuccess() {
        var orderMock = dataMock.getOrder();

        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(orderMock);
        Mockito.doNothing().when(producer).sendOrder(Mockito.any(Order.class));

        var orderSaved = orderService.save(orderMock);

        assertEquals(orderMock.getZipCode(), orderSaved.getZipCode());
        assertTrue(orderSaved.getZipCode() != null);

    }

    @Test
    void shouldFailInSearchForNonExistingOrder() {
        var id = 1L;

        Throwable exception = assertThrows(BusinessException.class, () -> {
            Order order = orderService.findOrFailById(id);
        });

        assertEquals("O pedido de id: " + id + " nao existe na base de dados!", exception.getMessage());
    }

    @Test
    void shouldFindOrderWithSuccess() {
        var orderMock = dataMock.getOrder();
        var id = 1L;

        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.of(orderMock));

        var order = orderService.findOrFailById(id);

        assertEquals(orderMock.getName(), order.getName());
        assertNotNull(order);

        Mockito.verify(orderRepository, Mockito.atLeastOnce()).findById(id);
    }

    @Test
    void shouldRemoveOrderWithSuccess() {
        var orderMock = dataMock.getOrder();
        var id = 1L;

        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.of(orderMock));
        Mockito.doNothing().when(orderRepository).deleteById(1L);

        orderService.deleteById(id);

        Mockito.verify(orderRepository, Mockito.atLeastOnce()).findById(id);
        Mockito.verify(orderRepository, Mockito.atLeastOnce()).deleteById(id);
    }

    @Test
    void shouldFailWhenRemoveNonExistingOrder() {
        var id = 1L;

        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(BusinessException.class, () -> orderService.deleteById(id));

        assertEquals("O pedido de id: " + id + " nao existe na base de dados!", exception.getMessage());
    }
}
