package com.mehmetsolak.userservice.grpc.utilities;

import com.google.protobuf.Timestamp;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class TimestampUtility {

    public static Timestamp toGrpcTimestamp(LocalDateTime date) {
        return Timestamp
                .newBuilder()
                .setSeconds(date.toEpochSecond(ZoneOffset.UTC))
                .setNanos(date.getNano())
                .build();
    }
}
