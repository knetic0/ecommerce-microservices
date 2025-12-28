package com.mehmetsolak.emailservice.infrastructure.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mehmetsolak.emailservice.infrastructure.ObjectMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class ObjectMapperServiceImpl implements ObjectMapperService {

    private final ObjectMapper objectMapper;

    @Override
    public <T> T fromJsonNode(JsonNode json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.treeToValue(json, clazz);
    }

    @Override
    public <T> JsonNode toJsonNode(T object) throws JsonProcessingException {
        return objectMapper.valueToTree(object);
    }
}
