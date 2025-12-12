package com.mehmetsolak.authservice.grpc.utilities;

import com.mehmetsolak.authservice.security.CustomUserDetails;
import com.mehmetsolak.proto.user.User;

public final class Mapper {

    public static CustomUserDetails toCustomUserDetails(User user) {
        return new CustomUserDetails(user);
    }
}
