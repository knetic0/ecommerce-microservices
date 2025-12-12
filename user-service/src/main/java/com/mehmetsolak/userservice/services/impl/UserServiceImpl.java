package com.mehmetsolak.userservice.services.impl;

import com.mehmetsolak.results.Result;
import com.mehmetsolak.userservice.dtos.UserCreateRequestDto;
import com.mehmetsolak.userservice.dtos.UserLoginRequestDto;
import com.mehmetsolak.userservice.dtos.UserResponseDto;
import com.mehmetsolak.userservice.entities.User;
import com.mehmetsolak.userservice.repository.UserRepository;
import com.mehmetsolak.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public Result<UserResponseDto> findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .map(user -> Result.success(UserResponseDto.from(user)))
                .orElse(Result.failure("User not found"));
    }

    @Override
    public Result<UserResponseDto> create(UserCreateRequestDto request) {
        Boolean isExists = userRepository.existsByEmail(request.getEmail());
        if(isExists) {
            return Result.failure("User already exists");
        }
        String hashed = passwordEncoder.encode(request.getPassword());
        User user = request.toEntity();
        user.setPassword(hashed);
        User saved = userRepository.save(user);
        return Result.success(UserResponseDto.from(saved));
    }

    @Override
    public Result<UserResponseDto> authenticate(UserLoginRequestDto request) {
        Result<User> userResult = getByEmail(request.getEmail());
        if(!userResult.isSuccess()) {
            return Result.failure(userResult.getMessage());
        }
        User user = userResult.getData();
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return Result.failure("Incorrect password");
        }
        return Result.success(UserResponseDto.from(user));
    }

    private Result<User> getByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .map(Result::success)
                .orElse(Result.failure("User not found"));
    }
}
