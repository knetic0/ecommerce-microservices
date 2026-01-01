package com.mehmetsolak.userservice.grpc.utilities;

import com.mehmetsolak.proto.user.Gender;
import com.mehmetsolak.proto.user.Role;
import com.mehmetsolak.proto.user.User;
import com.mehmetsolak.userservice.dtos.UserResponseDto;

import java.util.Objects;

public final class UserMapper {

    public static User toGrpcUser(UserResponseDto user) {
        return User
                .newBuilder()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setPhoneNumber(user.getPhoneNumber())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setRole(Role.valueOf(user.getRole().name()))
                .setGender(Gender.valueOf(user.getGender().name()))
                .setProfileImageUrl(Objects.requireNonNullElse(user.getProfileImageUrl(), ""))
                .setCreatedAt(TimestampUtility.toGrpcTimestamp(user.getCreatedAt()))
                .setUpdatedAt(TimestampUtility.toGrpcTimestamp(user.getUpdatedAt()))
                .build();
    }
}
