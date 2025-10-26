package com.food.ordering.system.payment.service.domain.ports.output.messager.publisher;


import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.payment.service.domain.event.PaymentFailedEvent;

//Không liên quan đến DB, mà liên quan đến GỬI MESSAGE RA NGOÀI HỆ THỐNG
//Gửi (event/message) đến các service khác -> Gửi đến Order-Service
//Adapter sẽ implements -> gửi message ra Kafka
public interface PaymentFailedMessagePublisher extends DomainEventPublisher<PaymentFailedEvent> {

}
