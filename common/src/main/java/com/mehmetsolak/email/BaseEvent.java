package com.mehmetsolak.email;

import java.util.UUID;

public sealed class BaseEvent permits WelcomeEvent {

    private final UUID eventId;

    public BaseEvent() {
        this.eventId = UUID.randomUUID();
    }

    public UUID getEventId() {
        return eventId;
    }
}
