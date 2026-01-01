package com.mehmetsolak.fileservice.producers.impl;

import com.mehmetsolak.file.UserProfilePictureEvent;
import com.mehmetsolak.fileservice.producers.UserProfilePictureEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UserProfilePictureEventProducerImpl
        implements UserProfilePictureEventProducer {

    private static final String TOPIC = "user-profile-picture";
    private final KafkaTemplate<String, UserProfilePictureEvent> kafkaTemplate;

    @Override
    public void send(String userId, String url) {
        UserProfilePictureEvent event = new UserProfilePictureEvent(userId, url);
        kafkaTemplate.send(TOPIC, event);
    }
}
