package com.mehmetsolak.authservice.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class LoginRequest {

    @NotNull(message = "{login.username.null.message}")
    @NotBlank(message = "{login.username.empty.message}")
    @Email(message = "{login.username.email.message}")
    private String username;

    @NotNull(message = "{login.password.null.message}")
    @NotBlank(message = "{login.password.empty.message}")
    private String password;
}
