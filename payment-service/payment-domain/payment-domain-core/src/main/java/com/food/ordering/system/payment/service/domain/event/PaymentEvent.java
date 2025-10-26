package com.food.ordering.system.payment.service.domain.event;

import com.food.ordering.system.domain.event.DomainEvent;
import com.food.ordering.system.payment.service.domain.entity.Payment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
@Setter
// Sự kiện Payment-Service phát ra gửi qua Kafka (gửi thông tin thay đổi qua các service khác)
// -> KL: Thông báo cho hệ thống biết chuyện gì xảy ra trong domain payment
// Làm template chung cho các class kế thừa (các event trong payment: complete, cancel, fail)
public abstract class PaymentEvent implements DomainEvent<Payment> {
    private final Payment payment;
    private final ZonedDateTime createdAt;
    private final List<String> failureMessage;

}
