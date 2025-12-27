package com.mehmetsolak.emailservice.consumers;

import com.mehmetsolak.emailservice.infrastructure.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;
import com.mehmetsolak.email.WelcomeEvent;

@Component
@RequiredArgsConstructor
public final class EmailConsumer {

    private final EmailService emailService;

    @KafkaListener(
            topics = "welcome-email",
            groupId = "email-service"
    )
    @RetryableTopic(
            attempts = "5",
            backOff = @BackOff(
                    delay = 2000,
                    multiplier = 2
            ),
            dltTopicSuffix = "-dlq"
    )
    public void consumeWelcomeEmail(WelcomeEvent event) {
        emailService.sendWelcome(event);
    }
}
