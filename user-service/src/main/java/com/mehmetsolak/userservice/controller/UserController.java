package com.mehmetsolak.userservice.controller;

import com.mehmetsolak.constants.CustomHeaders;
import com.mehmetsolak.results.Result;
import com.mehmetsolak.userservice.dtos.UserResponseDto;
import com.mehmetsolak.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public Result<UserResponseDto> me(@RequestHeader(name = CustomHeaders.USER_ID) String userId) {
        return userService.findById(userId);
    }
}
