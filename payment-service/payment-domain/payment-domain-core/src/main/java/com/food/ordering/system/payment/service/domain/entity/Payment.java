package com.food.ordering.system.payment.service.domain.entity;

import com.food.ordering.system.order.service.domain.valueobject.Money;
import com.food.ordering.system.payment.service.domain.valueobject.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
public class Payment {
    private UUID paymentId;
    private final UUID orderId;
    private final UUID customerId;
    private final Money price;

    private PaymentStatus paymentStatus;
    private ZonedDateTime createdAt;

    public void initializePayment() {
        paymentId = UUID.randomUUID();
        createdAt = ZonedDateTime.now(ZoneId.of("UTC"));
    }

    //  Không dừng chương trình, chỉ ghi nhận lỗi. Khi muốn tổng hợp nhiều lỗi và trả ra kết quả xử lý tổng thể sau cùng.
    public void validatePayment(List<String> failureMessage) {
        if(price == null || !price.isGreaterThanZero()) {
            failureMessage.add("Total price must be greater than zero");
        }
    }
}
