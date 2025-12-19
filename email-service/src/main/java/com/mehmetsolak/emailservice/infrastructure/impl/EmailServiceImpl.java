package com.mehmetsolak.emailservice.infrastructure.impl;

import com.mehmetsolak.emailservice.infrastructure.EmailService;
import com.mehmetsolak.emailservice.templates.WelcomeEmailTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.mehmetsolak.email.WelcomeEvent;

@Service
@RequiredArgsConstructor
public final class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    private void send(String to, String subject, String body) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendWelcome(WelcomeEvent event) {
        final String htmlBody = WelcomeEmailTemplate.BODY
                .replace("{{fullName}}", event.getFullName());

        send(event.getTo(), "Welcome to E-Commerce Application", htmlBody);
    }
}
