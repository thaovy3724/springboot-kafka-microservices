package com.swa.payment_service.order_domain.order_domain_core.entity;

import com.swa.order_service.order_domain.order_domain_core.exception.OrderDomainException;
import com.swa.payment_service.order_domain.order_domain_core.exception.PaymentDomainException;
import com.swa.payment_service.order_domain.order_domain_core.valueobject.Money;
import com.swa.payment_service.order_domain.order_domain_core.valueobject.PaymentStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.List;

@Getter
@Setter
@Builder
public class Payment {
    private UUID paymentId;
    private final UUID orderId;
    private final UUID customerId;
    private final Money price;

    private PaymentStatus paymentStatus;
    private ZonedDateTime createdAt;

    public void initializePayment() {
        paymentId=UUID.randomUUID();
        createdAt=ZonedDateTime.now(ZoneId.of("UTC"));
    }
// Dừng ngay logic, báo lỗi qua exception. Khi không thể xử lý tiếp được, ví dụ dữ liệu sai nghiêm trọng.
//    public void validatePayment(){
//        if(price == null || !price.isGreaterThanZero())
//            throw new OrderDomainException("Total price must greater than zero");
//    }
//  Không dừng chương trình, chỉ ghi nhận lỗi. Khi muốn tổng hợp nhiều lỗi và trả ra kết quả xử lý tổng thể sau cùng.
    public void validatePayment(List<String> failureMessages){
        if(price == null || !price.isGreaterThanZero()){
            failureMessages.add("Total price must greater than zero");
        }
    }

// setter lombok
//    public void updatePaymentStatus(PaymentStatus paymentStatus){
//        this.paymentStatus=paymentStatus;
//    }



}
