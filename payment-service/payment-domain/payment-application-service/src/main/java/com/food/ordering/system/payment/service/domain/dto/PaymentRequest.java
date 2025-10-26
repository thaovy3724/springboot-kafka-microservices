package com.food.ordering.system.payment.service.domain.dto;

import com.food.ordering.system.payment.service.domain.valueobject.PaymentOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor // tạo constructor với đầy đủ tham số
public class PaymentRequest {
    private String id; // ID của request
    private String sagaId; // ID saga transaction — quản lý giao dịch phân tán giữa các microservices (Order ↔ Payment ↔ Restaurant).
    private String orderId;
    private String customerId;
    private BigDecimal price;
    private Instant createAt;

    @Setter
    private PaymentOrderStatus paymentOrderStatus;

}
