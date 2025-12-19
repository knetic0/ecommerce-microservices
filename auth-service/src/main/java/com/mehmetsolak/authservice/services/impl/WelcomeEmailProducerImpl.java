package com.mehmetsolak.authservice.services.impl;

import com.mehmetsolak.authservice.services.WelcomeEmailProducer;
import com.mehmetsolak.email.WelcomeEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class WelcomeEmailProducerImpl implements WelcomeEmailProducer {

    private static final String TOPIC = "welcome-email";
    private final KafkaTemplate<String, WelcomeEvent> kafkaTemplate;

    @Override
    public void send(String email, String fullName) {
        WelcomeEvent event = new WelcomeEvent(email, fullName);
        kafkaTemplate.send(TOPIC, event);
    }
}
