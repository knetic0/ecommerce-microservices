package com.mehmetsolak.authservice.services.impl;

import com.mehmetsolak.authservice.dtos.RefreshTokenRotationResult;
import com.mehmetsolak.authservice.entities.RefreshToken;
import com.mehmetsolak.authservice.repository.RefreshTokenRepository;
import com.mehmetsolak.authservice.services.RefreshTokenService;
import com.mehmetsolak.results.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${ecommerce-app.security.refresh.expiration}")
    private Long expiration;

    @Override
    public String create(String userId) {
        String rawToken = generateRandomString();
        RefreshToken refreshToken = generateRefreshToken(rawToken, userId);
        refreshTokenRepository.save(refreshToken);
        return rawToken;
    }

    @Override
    @Transactional
    public Result<RefreshTokenRotationResult> rotate(String token) {
        String tokenHash = hashToken(token);

        Optional<RefreshToken> result = refreshTokenRepository
                .findByToken(tokenHash);

        if (result.isEmpty()) {
            return Result.failure("Refresh token not found");
        }

        RefreshToken oldRefreshToken = result.get();
        if(!oldRefreshToken.isValid()) {
            return Result.failure("Refresh token is invalid");
        }

        oldRefreshToken.setUsed(true);

        String newRawToken = generateRandomString();
        RefreshToken newRefreshToken = generateRefreshToken(newRawToken, String.valueOf(oldRefreshToken.getUserId()));

        refreshTokenRepository.save(oldRefreshToken);
        refreshTokenRepository.save(newRefreshToken);

        return Result.success(
                RefreshTokenRotationResult.builder()
                        .rawRefreshToken(newRawToken)
                        .userId(newRefreshToken.getUserId())
                        .build()
        );
    }

    @Override
    @Transactional
    public void revoke(String rawRefreshToken) {
        String tokenHash = hashToken(rawRefreshToken);

        refreshTokenRepository.findByToken(tokenHash)
                .ifPresent(token -> {
                    token.setRevoked(true);
                    token.setUsed(true);
                    refreshTokenRepository.save(token);
                });
    }

    private RefreshToken generateRefreshToken(String token, String userId) {
        String hashedToken = hashToken(token);

        return RefreshToken
                .builder()
                .token(hashedToken)
                .userId(UUID.fromString(userId))
                .expiresAt(LocalDateTime.now().plusSeconds(expiration / 1000))
                .build();
    }

    private String generateRandomString() {
        return UUID.randomUUID() + UUID.randomUUID().toString();
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception ex) {
            throw new IllegalStateException("Refresh token hashlenemedi", ex);
        }
    }
}
