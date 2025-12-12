package com.example.fileprocessor.exception;

import com.example.fileprocessor.payload.response.RowError;
import lombok.Getter;

import java.util.List;

public class RowProcessingException extends RuntimeException {
    @Getter
    private final List<RowError> errors;

    public RowProcessingException(List<RowError> errors) {
        super("Errors occurred while processing rows: " + errors);
        this.errors = errors;
    }
}
