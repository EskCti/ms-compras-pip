package com.eskcti.workervalidator.services;

import com.eskcti.workervalidator.models.Card;
import com.eskcti.workervalidator.models.Order;
import com.eskcti.workervalidator.services.emails.EmailService;
import com.eskcti.workervalidator.services.exceptions.InsufficientFundsException;
import com.eskcti.workervalidator.services.exceptions.LimitUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ValidatorService {

    @Autowired
    private EmailService emailService;

    public void validateOrder(Order order) {
        validateAvailableLimit(order.getCard());
        validarCompraComLimite(order);
        emailService.notifyCustomerPurchaseSuccessfully(order);
    }

    private void validarCompraComLimite(Order order) {
        if (order.getValueOrder().longValue() > order.getCard().getAvailableLimit().longValue()) {
            log.error("Valor do pedido: {}. Limite disponivel: {}", order.getValueOrder(), order.getCard().getAvailableLimit());
            throw new InsufficientFundsException("Voce nao tem limite para efetuar essa compra!");
        }
    }

    private void validateAvailableLimit(Card card) {
        if (card.getAvailableLimit().longValue() <= 0)
            throw new LimitUnavailableException("Limite indisponivel!");
    }
}
