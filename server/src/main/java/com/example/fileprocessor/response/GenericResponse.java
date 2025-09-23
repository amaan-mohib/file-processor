package com.example.fileprocessor.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericResponse {
    private String message;
    private Integer status = 200;

    public GenericResponse(String message) {
        this.message = message;
    }
}
