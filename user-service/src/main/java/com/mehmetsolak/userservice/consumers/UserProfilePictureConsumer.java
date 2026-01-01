package com.mehmetsolak.userservice.consumers;

import com.mehmetsolak.file.UserProfilePictureEvent;
import com.mehmetsolak.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProfilePictureConsumer {

    private final UserService userService;

    @KafkaListener(
            topics = "user-profile-picture",
            groupId = "file-service"
    )
    public void consume(UserProfilePictureEvent event) {
        userService.updateProfileImage(event.getUserId(), event.getUrl());
    }
}
