package com.mehmetsolak.authservice.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class RefreshTokenResponse {
    private String token;
}
