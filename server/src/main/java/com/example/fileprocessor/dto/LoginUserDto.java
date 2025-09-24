package com.example.fileprocessor.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class LoginUserDto {
    @NotEmpty(message = "The email address is required.")
    private String email;

    @NotEmpty(message = "The password is required.")
    private String password;
}
