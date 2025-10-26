package com.swa.payment_service.order_domain.order_application_service.ports.ouput;

import com.swa.payment_service.order_domain.order_domain_core.entity.Payment;
import org.springframework.stereotype.Service;

//Output port: Hệ thống gọi ra bên ngoài (Kafka, DB,...)
import java.util.Optional;
import java.util.UUID;
// Định nghĩa giao tiếp ra bên ngoài với DB
@Service
public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findByOrderId(UUID orderId);
}
