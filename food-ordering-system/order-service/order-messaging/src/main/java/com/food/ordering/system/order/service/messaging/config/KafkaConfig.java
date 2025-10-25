package com.food.ordering.system.order.service.messaging.config;

import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers;
    @Bean
    public ProducerFactory<String, MyMsg> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        return new DefaultKafkaProducerFactory<>(config);
    }
    @Bean
    public NewTopic taskTopic() {
        return TopicBuilder.name("topic-name")
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public KafkaTemplate<String, MyMsg> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
