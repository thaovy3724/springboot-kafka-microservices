package com.food.ordering.system.payment.service.dataaccess.mapper;

import com.food.ordering.system.order.service.domain.valueobject.Money;
import com.food.ordering.system.payment.service.dataaccess.entity.PaymentEntity;
import com.food.ordering.system.payment.service.domain.entity.Payment;
import org.springframework.stereotype.Component;

// Domain entity <-> Dataaccess Entity
@Component
public class PaymentDataAccessMapper {
    public PaymentEntity paymentToPaymentEntity(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getPaymentId())
                .customerId(payment.getCustomerId())
                .orderId(payment.getOrderId())
                .price(payment.getPrice().getAmount())
                .status(payment.getPaymentStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }

    public Payment paymentEntityToPayment(PaymentEntity paymentEntity) {
        return Payment.builder()
                .paymentId(paymentEntity.getId())
                .customerId(paymentEntity.getCustomerId())
                .orderId(paymentEntity.getOrderId())
                .price(new Money(paymentEntity.getPrice()))
                .createdAt(paymentEntity.getCreatedAt())
                .build();
    }
}
