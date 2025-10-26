package com.food.ordering.system.payment.service.domain.mapper;

import com.food.ordering.system.order.service.domain.valueobject.Money;
import com.food.ordering.system.payment.service.domain.dto.PaymentRequest;
import com.food.ordering.system.payment.service.domain.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
// DTO <-> Entity
public class PaymentDataMapper {
    public Payment paymentRequestToPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .orderId(UUID.fromString(paymentRequest.getOrderId()))
                .customerId(UUID.fromString(paymentRequest.getCustomerId()))
                .price(new Money(paymentRequest.getPrice()))
                .build();
    }
}
