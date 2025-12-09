package com.mehmetsolak.authservice.controllers;

import com.mehmetsolak.authservice.dtos.LoginRequest;
import com.mehmetsolak.authservice.dtos.LoginResponse;
import com.mehmetsolak.authservice.dtos.RegisterRequest;
import com.mehmetsolak.authservice.security.CustomUserDetails;
import com.mehmetsolak.authservice.services.JwtService;
import com.mehmetsolak.proto.user.*;
import com.mehmetsolak.results.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public final class AuthController {

    private final JwtService jwtService;
    private final UserServiceGrpc.UserServiceBlockingStub userServiceStub;

    @PostMapping("/login")
    public ResponseEntity<Result<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthenticateUserRequest grpcRequest = AuthenticateUserRequest
                .newBuilder()
                .setEmail(request.getUsername())
                .setPassword(request.getPassword())
                .build();

        AuthenticateUserResponse grpcResponse = userServiceStub.authenticateUser(grpcRequest);
        if(!grpcResponse.getIsValid()) {
            return ResponseEntity
                    .status(403)
                    .body(Result.failure(grpcResponse.getErrorMessage()));
        }

        CustomUserDetails userDetails = new CustomUserDetails(grpcResponse.getUser());
        String token = jwtService.generateToken(userDetails, Map.of());

        return ResponseEntity.ok(
                Result.success(LoginResponse
                    .builder()
                    .token(token)
                    .build()
                )
        );
    }

    @PostMapping("/register")
    public ResponseEntity<Result<?>> register(@Valid @RequestBody RegisterRequest request) {
        UserCreateRequest grpcRequest = UserCreateRequest
                .newBuilder()
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setEmail(request.getEmail())
                .setPhoneNumber(request.getPhoneNumber())
                .setPassword(request.getPassword())
                .setGender(Gender.valueOf(request.getGender().name()))
                .setRole(Role.valueOf(request.getRole().name()))
                .build();

        UserCreateResponse response = userServiceStub.createUser(grpcRequest);
        if(!response.getIsSuccess()) {
            return ResponseEntity
                    .badRequest()
                    .body(Result.failure(response.getErrorMessage()));
        }

        return ResponseEntity.ok(Result.success("Kullanıcı başarıyla kaydedildi!"));
    }
}
