package com.mehmetsolak.emailservice.registry;

import com.mehmetsolak.emailservice.application.EmailHandler;
import com.mehmetsolak.emailservice.enums.EmailType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public final class EmailHandlerRegistry {

    private final Map<EmailType, EmailHandler> handlers;

    public EmailHandlerRegistry(List<EmailHandler> handlerList) {
        this.handlers = handlerList.stream()
                .collect(Collectors.toMap(
                        EmailHandler::getType,
                        Function.identity()
                ));
    }

    public EmailHandler getHandler(EmailType type) {
        return handlers.get(type);
    }
}
