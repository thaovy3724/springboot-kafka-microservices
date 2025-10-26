package com.swa.payment_service.order_domain.order_application_service.mapper;

import com.swa.payment_service.order_domain.order_application_service.dto.PaymentRequest;
import com.swa.payment_service.order_domain.order_domain_core.entity.Payment;
import com.swa.payment_service.order_domain.order_domain_core.valueobject.Money;
import org.springframework.stereotype.Component;


import java.util.UUID;
@Component
// Mapping: DTO <-> Domain Entity
public class PaymentDataMapper {
    public Payment paymentRequestToPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .orderId(UUID.fromString(paymentRequest.getOrderId()))
                .customerId(UUID.fromString(paymentRequest.getCustomerId()))
                .price(new Money(paymentRequest.getPrice()))
                .build();
    }
}
