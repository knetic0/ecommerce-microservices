package com.mehmetsolak.userservice.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public final class UserLoginRequestDto {

    private String email;
    private String password;
}
