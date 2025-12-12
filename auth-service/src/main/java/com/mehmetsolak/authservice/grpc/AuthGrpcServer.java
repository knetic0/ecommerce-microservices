package com.mehmetsolak.authservice.grpc;

import com.mehmetsolak.authservice.grpc.utilities.Mapper;
import com.mehmetsolak.authservice.security.CustomUserDetails;
import com.mehmetsolak.authservice.services.JwtService;
import com.mehmetsolak.proto.auth.AuthServiceGrpc;
import com.mehmetsolak.proto.auth.IntrospectTokenRequest;
import com.mehmetsolak.proto.auth.IntrospectTokenResponse;
import com.mehmetsolak.proto.user.FindUserByEmailRequest;
import com.mehmetsolak.proto.user.User;
import com.mehmetsolak.proto.user.UserResponse;
import com.mehmetsolak.proto.user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public final class AuthGrpcServer extends AuthServiceGrpc.AuthServiceImplBase {

    private final JwtService jwtService;
    private final UserServiceGrpc.UserServiceBlockingStub userServiceStub;

    @Override
    public void introspectToken(
            IntrospectTokenRequest request,
            StreamObserver<IntrospectTokenResponse> responseObserver
    ) {
        try {
            String token = request.getToken();
            String username = jwtService.extractUsername(token);

            FindUserByEmailRequest grpcRequest = FindUserByEmailRequest
                    .newBuilder()
                    .setEmail(username)
                    .build();

            UserResponse response = userServiceStub.findUserByEmail(grpcRequest);
            if (!response.getIsSuccess()) {
                IntrospectTokenResponse resp = IntrospectTokenResponse
                        .newBuilder()
                        .setActive(false)
                        .build();

                responseObserver.onNext(resp);
                responseObserver.onCompleted();
                return;
            }

            User user = response.getUser();
            CustomUserDetails userDetails = Mapper.toCustomUserDetails(user);

            boolean isTokenValid = jwtService.validateToken(token, userDetails);
            if (!isTokenValid) {
                IntrospectTokenResponse resp = IntrospectTokenResponse
                        .newBuilder()
                        .setActive(false)
                        .build();

                responseObserver.onNext(resp);
                responseObserver.onCompleted();
                return;
            }

            IntrospectTokenResponse resp = IntrospectTokenResponse
                    .newBuilder()
                    .setActive(true)
                    .setUserId(userDetails.getId())
                    .setRole(userDetails.getRole())
                    .build();

            responseObserver.onNext(resp);
            responseObserver.onCompleted();
        }
        catch (Exception e) {
            IntrospectTokenResponse resp = IntrospectTokenResponse
                    .newBuilder()
                    .setActive(false)
                    .build();
            responseObserver.onNext(resp);
            responseObserver.onCompleted();
        }
    }
}
