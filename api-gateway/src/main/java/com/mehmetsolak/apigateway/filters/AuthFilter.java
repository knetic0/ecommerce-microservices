package com.mehmetsolak.apigateway.filters;

import com.mehmetsolak.proto.auth.AuthServiceGrpc;
import com.mehmetsolak.proto.auth.IntrospectTokenRequest;
import org.jspecify.annotations.NullMarked;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@NullMarked
public final class AuthFilter extends AbstractGatewayFilterFactory<Object> {

    private final AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub;

    public AuthFilter(AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub) {
        super(Object.class);
        this.authServiceBlockingStub = authServiceBlockingStub;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String authHeader = exchange
                    .getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            if(authHeader == null || !authHeader.startsWith("Bearer ")) {
                return unauthorized(exchange);
            }

            String token = authHeader.substring(7);
            IntrospectTokenRequest request = IntrospectTokenRequest
                    .newBuilder()
                    .setToken(token)
                    .build();

            return Mono
                    .fromCallable(() -> authServiceBlockingStub.introspectToken(request))
                    .subscribeOn(reactor.core.scheduler.Schedulers.boundedElastic())
                    .flatMap(response -> {
                        if(!response.getActive()) {
                            return unauthorized(exchange);
                        }
                        return chain.filter(exchange);
                    });
        };
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
