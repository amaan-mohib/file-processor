package com.example.fileprocessor.payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserDto {
    @NotEmpty(message = "The name is required.")
    private String name;

    @NotEmpty(message = "The email address is required.")
    private String email;

    @NotEmpty(message = "The password is required.")
    private String password;
}
