package com.mehmetsolak.fileservice.producers;

public interface UserProfilePictureEventProducer {
    void send(String userId, String url);
}
