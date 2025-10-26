// Dữ liệu mà order-service gửi sang payment-service qua kafka khi đơn hàng được thanh toán
package com.swa.payment_service.order_domain.order_application_service.dto;

import com.swa.payment_service.order_domain.order_domain_core.valueobject.PaymentOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PaymentRequest {
    private String id; // ID của request
    private String sagaId; // ID saga transaction — quản lý giao dịch phân tán giữa các microservices (Order ↔ Payment ↔ Restaurant).
    private String orderId;
    private String customerId;
    private BigDecimal price;
    private Instant createAt;
    private PaymentOrderStatus paymentOrderStatus;

}
