package com.mehmetsolak.authservice.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public final class LoginResponse {

    private String token;
}
