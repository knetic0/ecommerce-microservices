package com.mehmetsolak.authservice.dtos;

import com.mehmetsolak.enums.UserGender;
import com.mehmetsolak.enums.UserRole;
import lombok.Data;

@Data
public final class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private UserRole role;
    private UserGender gender;
}
