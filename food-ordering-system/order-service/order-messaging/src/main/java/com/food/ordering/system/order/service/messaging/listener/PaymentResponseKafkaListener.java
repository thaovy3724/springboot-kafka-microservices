package com.food.ordering.system.order.service.messaging.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentResponseKafkaListener {
    @KafkaListener(topics = {"payment-topic"}, groupId = "payment-topic")
    public void consume(Object obj) {
        log.info(String.format("Received: " + obj));
    }
}
