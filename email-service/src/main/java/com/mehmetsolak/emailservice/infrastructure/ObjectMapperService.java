package com.mehmetsolak.emailservice.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ObjectMapperService {
    <T> String serialize(T object) throws JsonProcessingException;
    <T> T deserialize(String json, Class<T> clazz) throws JsonProcessingException;
}
