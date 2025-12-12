package com.example.fileprocessor.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class RowError {
    private final int rowIndex;
    @Getter
    private final Throwable cause;

    @Override
    public String toString() {
        return "Row " + rowIndex + ": " + cause.getMessage();
    }
}
