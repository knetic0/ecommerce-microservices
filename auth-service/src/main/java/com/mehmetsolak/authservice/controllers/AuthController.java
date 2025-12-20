package com.mehmetsolak.authservice.controllers;

import com.mehmetsolak.authservice.dtos.*;
import com.mehmetsolak.authservice.security.CustomUserDetails;
import com.mehmetsolak.authservice.security.RefreshTokenCookieFactory;
import com.mehmetsolak.authservice.services.JwtService;
import com.mehmetsolak.authservice.services.RefreshTokenService;
import com.mehmetsolak.authservice.services.WelcomeEmailProducer;
import com.mehmetsolak.proto.user.*;
import com.mehmetsolak.results.Result;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public final class AuthController {

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenCookieFactory refreshTokenCookieFactory;
    private final UserServiceGrpc.UserServiceBlockingStub userServiceStub;
    private final WelcomeEmailProducer welcomeEmailProducer;

    @PostMapping("/login")
    public ResponseEntity<Result<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
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
        String token = jwtService.generateToken(userDetails, Map.of("role", userDetails.getRole()));

        String refreshToken = refreshTokenService.create(userDetails.getId());

        ResponseCookie refreshCookie = refreshTokenCookieFactory.create(refreshToken);
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

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

        User user = response.getUser();
        welcomeEmailProducer.send(user.getEmail(), user.getFirstName() + " " + user.getLastName());

        return ResponseEntity.ok(Result.success("Kullanıcı başarıyla kaydedildi!"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Result<RefreshTokenResponse>> refreshToken(
            @CookieValue(name = "refresh_token", required = false) String refreshToken,
            HttpServletResponse response
    ) {
        if (refreshToken == null) {
            return ResponseEntity.status(401)
                    .body(Result.failure("Refresh token is null"));
        }

        Result<RefreshTokenRotationResult> rotationResult = refreshTokenService.rotate(refreshToken);
        if (!rotationResult.isSuccess()) {
            return ResponseEntity.status(401)
                    .body(Result.failure(rotationResult.getMessage()));
        }
        RefreshTokenRotationResult rotatedRefreshToken = rotationResult.getData();

        FindUserByIdRequest request = FindUserByIdRequest
                .newBuilder()
                .setId(String.valueOf(rotatedRefreshToken.getUserId()))
                .build();

        UserResponse userResponse = userServiceStub.findUserById(request);
        if (!userResponse.getIsSuccess()) {
            return ResponseEntity.status(401)
                    .body(Result.failure(userResponse.getErrorMessage()));
        }

        CustomUserDetails userDetails = new CustomUserDetails(userResponse.getUser());
        String accessToken = jwtService.generateToken(userDetails, Map.of("role", userDetails.getRole()));

        ResponseCookie refreshCookie = refreshTokenCookieFactory.create(rotatedRefreshToken.getRawRefreshToken());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return ResponseEntity.ok(Result.success(
                RefreshTokenResponse.builder()
                        .token(accessToken)
                        .build()
                )
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(name = "refresh_token", required = false) String refreshToken,
            HttpServletResponse response
    ) {
        if (refreshToken != null) {
            refreshTokenService.revoke(refreshToken);
        }

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                refreshTokenCookieFactory.delete().toString()
        );

        return ResponseEntity.noContent().build();
    }
}
