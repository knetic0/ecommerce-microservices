package com.mehmetsolak.emailservice.infrastructure.impl;

import com.mehmetsolak.email.BaseEvent;
import com.mehmetsolak.email.WelcomeEvent;
import com.mehmetsolak.emailservice.entity.EmailOutbox;
import com.mehmetsolak.emailservice.enums.EmailStatus;
import com.mehmetsolak.emailservice.enums.EmailType;
import com.mehmetsolak.emailservice.infrastructure.EmailDlqService;
import com.mehmetsolak.emailservice.infrastructure.ObjectMapperService;
import com.mehmetsolak.emailservice.repository.EmailOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public final class EmailDlqServiceImpl implements EmailDlqService {

    private final EmailOutboxRepository emailOutboxRepository;
    private final ObjectMapperService objectMapperService;

    @Override
    @KafkaListener(
            topics = "welcome-email-dlq",
            groupId = "email-dlq-group"
    )
    public void consumeWelcomeEmailDlq(WelcomeEvent event) {
        createOutbox(
                event,
                EmailType.WELCOME_EMAIL
        );
    }

    private <T extends BaseEvent> void createOutbox(T event, EmailType emailType) {
        try {
            EmailOutbox emailOutbox = EmailOutbox.builder()
                    .eventId(event.getEventId())
                    .payload(objectMapperService.toJsonNode(event))
                    .status(EmailStatus.FAILED)
                    .type(emailType)
                    .retryCount(0)
                    .build();

            emailOutboxRepository.save(emailOutbox);
        } catch(DataIntegrityViolationException e) {
            log.info("Duplicate event ignored: eventId={}", event.getEventId());
        } catch (Exception e) {
            log.error("CRITICAL: Failed to save to outbox: eventId={}", event.getEventId(), e);
        }
    }
}
