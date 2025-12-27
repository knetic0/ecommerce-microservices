package com.mehmetsolak.emailservice.application.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mehmetsolak.email.WelcomeEvent;
import com.mehmetsolak.emailservice.application.EmailHandler;
import com.mehmetsolak.emailservice.enums.EmailType;
import com.mehmetsolak.emailservice.infrastructure.EmailService;
import com.mehmetsolak.emailservice.infrastructure.ObjectMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class SendWelcomeEmailHandler implements EmailHandler {

    private final EmailService emailService;
    private final ObjectMapperService objectMapperService;

    @Override
    public EmailType getType() {
        return EmailType.WELCOME_EMAIL;
    }

    @Override
    public void handle(String payload) throws JsonProcessingException {
        WelcomeEvent event = objectMapperService.deserialize(payload, WelcomeEvent.class);
        emailService.sendWelcome(event);
    }
}
