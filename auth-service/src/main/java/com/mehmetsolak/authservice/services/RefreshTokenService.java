package com.mehmetsolak.authservice.services;

import com.mehmetsolak.authservice.dtos.RefreshTokenRotationResult;
import com.mehmetsolak.results.Result;

public interface RefreshTokenService {
    String create(String userId);
    Result<RefreshTokenRotationResult> rotate(String refreshToken);
    void revoke(String refreshToken);
}
