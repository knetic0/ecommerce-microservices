package com.mehmetsolak.userservice.grpc;

import com.mehmetsolak.enums.UserGender;
import com.mehmetsolak.enums.UserRole;
import com.mehmetsolak.proto.user.*;
import com.mehmetsolak.results.Result;
import com.mehmetsolak.userservice.dtos.UserCreateRequestDto;
import com.mehmetsolak.userservice.dtos.UserLoginRequestDto;
import com.mehmetsolak.userservice.dtos.UserResponseDto;
import com.mehmetsolak.userservice.grpc.utilities.UserMapper;
import com.mehmetsolak.userservice.services.UserService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public final class UserGrpcServer extends UserServiceGrpc.UserServiceImplBase {

    private final UserService userService;

    @Override
    public void createUser(UserCreateRequest request, StreamObserver<UserCreateResponse> responseObserver) {

        UserCreateRequestDto dto = UserCreateRequestDto
                .builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .password(request.getPassword())
                .userRole(UserRole.valueOf(request.getRole().name()))
                .userGender(UserGender.valueOf(request.getGender().name()))
                .build();

        Result<UserResponseDto> result = userService.create(dto);

        if(!result.isSuccess()) {
            UserCreateResponse response = UserCreateResponse
                    .newBuilder()
                    .setIsSuccess(false)
                    .setErrorMessage(result.getMessage())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }

        User grpcUser = UserMapper.toGrpcUser(result.getData());

        UserCreateResponse response = UserCreateResponse
                .newBuilder()
                .setUser(grpcUser)
                .setIsSuccess(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void findUserByEmail(
            FindUserByEmailRequest request,
            StreamObserver<UserResponse> responseObserver) {

        Result<UserResponseDto> result = userService.findByEmail(request.getEmail());

        if (!result.isSuccess()) {
            UserResponse response = UserResponse
                    .newBuilder()
                    .setIsSuccess(false)
                    .setErrorMessage("User not found with email: " + request.getEmail())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }

        User grpcUser = UserMapper.toGrpcUser(result.getData());

        UserResponse response = UserResponse
                .newBuilder()
                .setUser(grpcUser)
                .setIsSuccess(true)
                .setErrorMessage("")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void findUserById(
            FindUserByIdRequest request,
            StreamObserver<UserResponse> responseObserver
    ) {
        Result<UserResponseDto> result = userService.findById(request.getId());

        if (!result.isSuccess()) {
            UserResponse response = UserResponse
                    .newBuilder()
                    .setIsSuccess(false)
                    .setErrorMessage("User not found.")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }

        User grpcUser = UserMapper.toGrpcUser(result.getData());

        UserResponse response = UserResponse
                .newBuilder()
                .setUser(grpcUser)
                .setIsSuccess(true)
                .setErrorMessage("")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void authenticateUser(
            AuthenticateUserRequest request,
            StreamObserver<AuthenticateUserResponse> responseObserver) {

        UserLoginRequestDto requestDto = UserLoginRequestDto
                .builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        Result<UserResponseDto> result = userService.authenticate(requestDto);
        if (!result.isSuccess()) {
            AuthenticateUserResponse response = AuthenticateUserResponse
                    .newBuilder()
                    .setIsValid(false)
                    .setErrorMessage(result.getMessage())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }

        AuthenticateUserResponse response = AuthenticateUserResponse
                .newBuilder()
                .setIsValid(true)
                .setUser(UserMapper.toGrpcUser(result.getData()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
