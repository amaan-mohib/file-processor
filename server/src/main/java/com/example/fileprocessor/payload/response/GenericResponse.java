package com.example.fileprocessor.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericResponse<T> {
    private String message;
    private Integer status = 200;
    private T data = null;

    public GenericResponse(String message) {
        this.message = message;
    }

    public GenericResponse(String message, Integer status) {
        this.message = message;
        this.status = status;
    }
}
