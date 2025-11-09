package com.example.fileprocessor.payload.request;

import jakarta.validation.constraints.NotEmpty;

public record RefreshTokenDto(
        @NotEmpty(message = "The refresh token is required.") String refreshToken
) {
}
