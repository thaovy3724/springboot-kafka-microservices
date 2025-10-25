package com.food.ordering.system.order.service.messaging.config;

import lombok.Value;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic transactionTopic(){
        return TopicBuilder.name("order-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
