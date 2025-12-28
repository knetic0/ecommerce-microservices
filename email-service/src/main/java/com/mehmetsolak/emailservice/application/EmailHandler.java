package com.mehmetsolak.emailservice.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.mehmetsolak.emailservice.enums.EmailType;

public interface EmailHandler {
    EmailType getType();
    void handle(JsonNode payload) throws JsonProcessingException;
}
