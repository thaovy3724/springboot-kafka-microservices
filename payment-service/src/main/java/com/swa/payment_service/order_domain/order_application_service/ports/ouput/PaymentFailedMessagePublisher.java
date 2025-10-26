package com.swa.payment_service.order_domain.order_application_service.ports.ouput;

//Không liên quan đến DB, mà lq đến GỬI MESSAGE RA NGOÀI HỆ THỐNG
//Gửi (event/message) đến các service khác -> Gửi đến Order-Service
//Adapter sẽ implements -> gửi message ra Kafka
public interface PaymentFailedMessagePublisher {
}
