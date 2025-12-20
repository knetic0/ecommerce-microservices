package com.mehmetsolak.authservice.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RefreshTokenRotationResult {

    private String rawRefreshToken;
    private UUID userId;
}
