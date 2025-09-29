package com.example.fileprocessor.payload.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record JobSaveDto(
        @NotNull(message = "The file key is required.") UUID fileKey,
        @NotEmpty(message = "The query is required.") String query
) {}
