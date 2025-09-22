package com.example.fileprocessor.response;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private String message;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<String> errors;

    public ErrorResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public ErrorResponse(String message) {
        this.message = message;
        this.errors = new ArrayList<>();
    }
}
