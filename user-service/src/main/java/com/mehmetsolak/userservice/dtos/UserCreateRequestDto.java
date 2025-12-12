package com.mehmetsolak.userservice.dtos;

import com.mehmetsolak.enums.UserGender;
import com.mehmetsolak.enums.UserRole;
import com.mehmetsolak.userservice.entities.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
public final class UserCreateRequestDto {
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String password;
    private MultipartFile profileImage;
    private UserRole userRole;
    private UserGender userGender;

    public User toEntity() {
        return User
                .builder()
                .email(getEmail())
                .phoneNumber(getPhoneNumber())
                .firstName(getFirstName())
                .lastName(getLastName())
                .role(getUserRole())
                .gender(getUserGender())
                .build();
    }
}
