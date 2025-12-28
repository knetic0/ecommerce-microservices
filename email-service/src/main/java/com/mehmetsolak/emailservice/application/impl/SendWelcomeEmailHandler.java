package com.mehmetsolak.emailservice.application.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
    public void handle(JsonNode payload) throws JsonProcessingException {
        WelcomeEvent event = objectMapperService.fromJsonNode(payload, WelcomeEvent.class);
        emailService.sendWelcome(event);
    }
}
