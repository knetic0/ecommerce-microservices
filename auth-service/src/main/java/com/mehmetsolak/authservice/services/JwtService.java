package com.mehmetsolak.authservice.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String generateToken(UserDetails userDetails, Map<String, Object> claims);
    Boolean validateToken(String token, UserDetails userDetails);
    String extractUsername(String token);
    String extractRole(String token);
}
