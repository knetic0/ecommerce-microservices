package com.mehmetsolak.userservice.dtos;

import com.mehmetsolak.enums.UserGender;
import com.mehmetsolak.enums.UserRole;
import com.mehmetsolak.userservice.entities.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class UserResponseDto {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String profileImageUrl;
    private UserRole role;
    private UserGender gender;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .id(user.getId().toString())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .profileImageUrl(user.getProfileImageUrl())
                .role(user.getRole())
                .gender(user.getGender())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
