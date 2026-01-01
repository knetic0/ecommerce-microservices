package com.mehmetsolak.fileservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
public final class CloudinarySignatureService {

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    public String generateSignature(Map<String, String> params) {
        try {
            TreeMap<String, String> sorted = new TreeMap<>(params);

            String toSign = sorted.entrySet().stream()
                    .map(e -> e.getKey() + "=" + e.getValue())
                    .collect(Collectors.joining("&"));

            toSign += apiSecret;

            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(toSign.getBytes(StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();
            for (byte b : digest) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();

        } catch (Exception e) {
            throw new RuntimeException("Cloudinary signature generation failed", e);
        }
    }

    public String currentTimestamp() {
        return String.valueOf(Instant.now().getEpochSecond());
    }
}
