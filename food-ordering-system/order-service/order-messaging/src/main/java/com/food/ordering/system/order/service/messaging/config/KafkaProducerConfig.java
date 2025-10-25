package com.food.ordering.system.order.service.messaging.config;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.ProducerFactory;

public class KafkaProducerConfig {
    @Bean
    public ProducerFactory<UUID, TransactionMessage>
}
