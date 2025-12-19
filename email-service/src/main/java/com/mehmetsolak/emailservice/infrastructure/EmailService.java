package com.mehmetsolak.emailservice.infrastructure;

import com.mehmetsolak.email.WelcomeEvent;

public interface EmailService {
    void sendWelcome(WelcomeEvent event);
}
