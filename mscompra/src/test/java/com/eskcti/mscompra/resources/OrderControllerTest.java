package com.eskcti.mscompra.resources;

import com.eskcti.mscompra.MscompraApplication;
import com.eskcti.mscompra.models.Order;
import com.eskcti.mscompra.services.DataMock;
import com.eskcti.mscompra.services.OrderService;
import com.eskcti.mscompra.services.exception.EntityNotFoundException;
import com.eskcti.mscompra.services.rabbitmq.Producer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MscompraApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private Producer producer;

    private static final String URL_ORDER = "/orders";

    private DataMock dataMock = new DataMock();

    @Test
    void shouldRegisterOrderWithSuccess() throws Exception {
        var orderBody = dataMock.getOrder();
        var id = 1L;

        Mockito.doNothing().when(producer).sendOrder(Mockito.any(Order.class));

        mockMvc.perform(post(URL_ORDER)
                .content(mapper.writeValueAsString(orderBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Order order = orderService.findOrFailById(id);

        assertEquals(order.getId(), id);
        assertNotNull(order);
    }

    @Test
    void shouldFindOrderWithSuccess() throws Exception {
        var id = 1L;

        mockMvc.perform(get(URL_ORDER.concat("/" + id)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldFailWhenFindNonExistingOrder() throws Exception {
        var id = 2L;

        mockMvc.perform(get(URL_ORDER.concat("/" + id)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteOrderWithSuccess() throws Exception {
        var id = 1L;

        mockMvc.perform(delete(URL_ORDER.concat("/" + id)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Throwable exception = assertThrows(EntityNotFoundException.class,
                () -> orderService.deleteById(id));

        assertEquals("O pedido de id: " + id + " nao existe na base de dados!", exception.getMessage());
    }
}
