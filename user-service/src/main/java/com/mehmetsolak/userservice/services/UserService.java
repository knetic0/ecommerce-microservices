package com.mehmetsolak.userservice.services;

import com.mehmetsolak.results.Result;
import com.mehmetsolak.userservice.dtos.UserCreateRequestDto;
import com.mehmetsolak.userservice.dtos.UserLoginRequestDto;
import com.mehmetsolak.userservice.dtos.UserResponseDto;

public interface UserService {
    Result<UserResponseDto> findById(String id);
    Result<UserResponseDto> findByEmail(String email);
    Result<UserResponseDto> create(UserCreateRequestDto request);
    Result<UserResponseDto> authenticate(UserLoginRequestDto request);
    Result<?> updateProfileImage(String id, String url);
}
