package com.swa.payment_service.order_domain.order_application_service.ports.input;

import com.swa.payment_service.order_domain.order_application_service.dto.PaymentRequest;
import com.swa.payment_service.order_domain.order_domain_core.entity.Payment;
// Input port: Bên ngoài gọi vào hệ thống
//Entry point: Order-Service có thể yêu cầu 2 hành động như bên dưới (thông qua kafka)
public interface PaymentRequestMessageListener {
//  Khi đơn hàng được xác nhận (PaymentOrderStatus-PENDING) -> Yêu cầu Payment Thanh toán
    void completePayment(PaymentRequest paymentRequest);
//  Khi đơn hàng hủy (PaymenOrderStatus-CANCELLED) -> Payment Hủy thanh toán
    void cancelPayment(PaymentRequest paymentRequest);
}
