package com.swa.payment_service.order_domain.order_domain_core.event;

import com.swa.payment_service.order_domain.order_domain_core.entity.Payment;
import java.time.ZonedDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
// Sự kiện Payment-Service phát ra gửi qua Kafka ( gửi thông tin thay đổi qua các service khác)
// -> KL: Thông báo cho hệ thống biết chuyện gì xra trong domain payment
// Làm template chung cho các class kế thừa (các event trong payment: complete, cancel, fail)
public abstract class PaymentEvent{
    private final Payment payment;
    private final ZonedDateTime createdAt;
    private final List<String> failureMessages;
}
