package com.mehmetsolak.fileservice.config;

import com.mehmetsolak.file.UserProfilePictureEvent;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, UserProfilePictureEvent> userProfilePictureEventProducerFactoryproducerFactory(
            KafkaProperties properties
    ) {
        return new DefaultKafkaProducerFactory<>(properties.buildProducerProperties());
    }

    @Bean
    public KafkaTemplate<String, UserProfilePictureEvent> userProfilePictureEventKafkaTemplate(
            ProducerFactory<String, UserProfilePictureEvent> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }
}
