package com.mehmetsolak.emailservice.scheduler;

import com.mehmetsolak.email.WelcomeEvent;
import com.mehmetsolak.emailservice.entity.EmailOutbox;
import com.mehmetsolak.emailservice.enums.EmailStatus;
import com.mehmetsolak.emailservice.infrastructure.EmailService;
import com.mehmetsolak.emailservice.infrastructure.ObjectMapperService;
import com.mehmetsolak.emailservice.repository.EmailOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailOutboxScheduler {

    private final EmailOutboxRepository emailOutboxRepository;
    private final EmailService emailService;
    private final ObjectMapperService objectMapperService;

    @Value("${email-service.outbox.max-retry}")
    private Integer maxRetryCount;

    @Scheduled(
            fixedDelayString = "${email-service.outbox.scheduled.fixed-delay}",
            initialDelayString = "${email-service.outbox.scheduled.initial-delay}"
    )
    @Transactional
    public void processFailedEmails() {
        log.info("Starting to process failed emails from outbox");

        List<EmailOutbox> failedEmails = emailOutboxRepository
                .findByStatusAndRetryCountLessThan(EmailStatus.FAILED, maxRetryCount);

        if (failedEmails.isEmpty()) {
            log.info("No failed emails to process");
            return;
        }

        log.info("Found {} failed emails to process", failedEmails.size());

        for(EmailOutbox emailOutbox : failedEmails)
            process(emailOutbox);

        log.info("Finished processing failed emails from outbox");
    }

    private void process(EmailOutbox outbox) {
        log.info("Processing outbox - eventId: {}, type: {}, retryCount: {}",
                outbox.getEventId(), outbox.getType(), outbox.getRetryCount());

        try {
            switch (outbox.getType()) {
                case WELCOME_EMAIL -> {
                    WelcomeEvent event = objectMapperService.deserialize(
                            outbox.getPayload(), WelcomeEvent.class);

                    emailService.sendWelcome(event);
                }
                default -> {
                    log.warn("Unknown email type {}", outbox.getType());
                    return;
                }
            }

            outbox.setStatus(EmailStatus.SENT);
            log.info("Email sent successfully - eventId {}", outbox.getEventId());
        } catch (Exception e) {
            outbox.setRetryCount(outbox.getRetryCount() + 1);

            if (outbox.getRetryCount() >= maxRetryCount) {
                outbox.setStatus(EmailStatus.DEAD_LETTER);
                log.error("Email moved to DEAD_LETTER after 3 retries - eventId {}", outbox.getEventId());
            } else {
                log.warn("Email retry failed (attempt {}/3) - eventId: {}",
                        outbox.getRetryCount(), outbox.getEventId(), e);
            }
        }

        emailOutboxRepository.save(outbox);
    }
}
