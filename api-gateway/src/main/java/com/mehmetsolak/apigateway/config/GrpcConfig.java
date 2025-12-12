package com.mehmetsolak.apigateway.config;

import com.mehmetsolak.proto.auth.AuthServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {

    @Bean
    public ManagedChannel authServiceChannel(
            @Value("${auth.grpc.address}") String address,
            @Value("${auth.grpc.port}") Integer port
    ) {
        return ManagedChannelBuilder
                .forAddress(address, port)
                .usePlaintext()
                .build();
    }

    @Bean
    public AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub(
            ManagedChannel authServiceChannel
    ) {
        return AuthServiceGrpc.newBlockingStub(authServiceChannel);
    }
}
