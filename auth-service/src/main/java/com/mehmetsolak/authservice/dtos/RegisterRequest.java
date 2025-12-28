package com.mehmetsolak.authservice.dtos;

import com.mehmetsolak.enums.UserGender;
import com.mehmetsolak.enums.UserRole;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public final class RegisterRequest {

    @NotNull(message = "{register.firstName.null.message}")
    @NotBlank(message = "{register.firstName.empty.message}")
    @Size(max = 30, message = "{register.firstName.max.message}")
    private String firstName;

    @NotNull(message = "{register.lastName.null.message}")
    @NotBlank(message = "{register.lastName.empty.message}")
    @Size(max = 30, message = "{register.lastName.max.message}")
    private String lastName;

    @NotNull(message = "{register.email.null.message}")
    @NotBlank(message = "{register.email.empty.message}")
    @Email(message = "{register.email.invalid.message}")
    private String email;

    @NotNull(message = "{register.password.null.message}")
    @NotBlank(message = "{register.password.empty.message}")
    @Size(min = 8, message = "{register.password.min.message}")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9])\\S{8,}$",
            message = "{register.password.complexity.message}"
    )
    private String password;

    @NotNull(message = "{register.phoneNumber.null.message}")
    @NotBlank(message = "{register.phoneNumber.empty.message}")
    @Size(min = 12, max = 16, message = "{register.phoneNumber.size.message}")
    @Pattern(
            regexp = "^\\+[0-9]{11,15}$",
            message = "{register.phoneNumber.invalid.message}"
    )
    private String phoneNumber;

    private UserRole role;
    private UserGender gender;
}
