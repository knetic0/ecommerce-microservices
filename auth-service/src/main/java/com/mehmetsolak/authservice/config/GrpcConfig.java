package com.mehmetsolak.authservice.config;

import com.mehmetsolak.proto.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {

    @Bean
    public ManagedChannel userServiceChannel(
            @Value("${user.grpc.address}") String address,
            @Value("${user.grpc.port}") Integer port
    ) {
        return ManagedChannelBuilder
                .forAddress(address, port)
                .usePlaintext()
                .build();
    }

    @Bean
    public UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub(
            ManagedChannel userServiceChannel
    ) {
        return UserServiceGrpc.newBlockingStub(userServiceChannel);
    }
}
