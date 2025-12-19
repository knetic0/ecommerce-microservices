package com.mehmetsolak.authservice.services;

public interface WelcomeEmailProducer {
    void send(String email, String fullName);
}
