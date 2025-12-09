package com.example.fileprocessor.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class NotificationResponse {
    Long id;
    String message;
    boolean read;
    UUID jobKey;
    Instant createdAt;
}
