package com.mehmetsolak.authservice.config;

import com.mehmetsolak.email.WelcomeEvent;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, WelcomeEvent> producerFactory(
            KafkaProperties properties
    ) {
        return new DefaultKafkaProducerFactory<>(properties.buildProducerProperties());
    }

    @Bean
    public KafkaTemplate<String, WelcomeEvent> kafkaTemplate(
            ProducerFactory<String, WelcomeEvent> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }
}
