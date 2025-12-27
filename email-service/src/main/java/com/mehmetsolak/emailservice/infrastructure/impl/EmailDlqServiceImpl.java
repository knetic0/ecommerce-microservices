package com.mehmetsolak.emailservice.infrastructure.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mehmetsolak.email.BaseEvent;
import com.mehmetsolak.email.WelcomeEvent;
import com.mehmetsolak.emailservice.entity.EmailOutbox;
import com.mehmetsolak.emailservice.enums.EmailStatus;
import com.mehmetsolak.emailservice.enums.EmailType;
import com.mehmetsolak.emailservice.infrastructure.EmailDlqService;
import com.mehmetsolak.emailservice.infrastructure.ObjectMapperService;
import com.mehmetsolak.emailservice.repository.EmailOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class EmailDlqServiceImpl implements EmailDlqService {

    private final EmailOutboxRepository emailOutboxRepository;
    private final ObjectMapperService objectMapperService;

    @Override
    @KafkaListener(
            topics = "welcome-email-dlq",
            groupId = "email-dlq-group"
    )
    public void consumeWelcomeEmailDlq(WelcomeEvent event)
            throws JsonProcessingException {
        createOutbox(
                event,
                EmailType.WELCOME_EMAIL
        );
    }

    private <T extends BaseEvent> void createOutbox(T event, EmailType emailType)
            throws JsonProcessingException {
        EmailOutbox emailOutbox = EmailOutbox.builder()
                .eventId(event.getEventId())
                .payload(objectMapperService.serialize(event))
                .status(EmailStatus.FAILED)
                .type(emailType)
                .retryCount(0)
                .build();

        emailOutboxRepository.save(emailOutbox);
    }
}
