package com.mehmetsolak.emailservice.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface ObjectMapperService {
    <T> T fromJsonNode(JsonNode json, Class<T> clazz) throws JsonProcessingException;
    <T> JsonNode toJsonNode(T object) throws JsonProcessingException;
}
