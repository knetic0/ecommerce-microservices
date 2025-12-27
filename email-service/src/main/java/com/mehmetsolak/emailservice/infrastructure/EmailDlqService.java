package com.mehmetsolak.emailservice.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mehmetsolak.email.BaseEvent;
import com.mehmetsolak.email.WelcomeEvent;

public interface EmailDlqService {
    void consumeWelcomeEmailDlq(WelcomeEvent event);
}
