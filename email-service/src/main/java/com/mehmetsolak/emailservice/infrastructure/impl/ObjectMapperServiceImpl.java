package com.mehmetsolak.emailservice.infrastructure.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mehmetsolak.emailservice.infrastructure.ObjectMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class ObjectMapperServiceImpl implements ObjectMapperService {

    private final ObjectMapper objectMapper;

    @Override
    public <T> String serialize(T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    @Override
    public <T> T deserialize(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }
}
