package com.example.fileprocessor.controller;

import com.example.fileprocessor.dto.LoginUserDto;
import com.example.fileprocessor.dto.RegisterUserDto;
import com.example.fileprocessor.entity.User;
import com.example.fileprocessor.response.ErrorResponse;
import com.example.fileprocessor.response.LoginResponse;
import com.example.fileprocessor.service.AuthenticationService;
import com.example.fileprocessor.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    private LoginResponse LoginAndGetResponse(LoginUserDto loginUserDto) {
        User user = authenticationService.login(loginUserDto);
        String jwtToken = jwtService.generateToken(user.getEmail());
        return new LoginResponse(jwtToken, jwtService.getExpiration());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.register(registerUserDto);
        if (registeredUser == null) {
            return new ResponseEntity<>(new ErrorResponse("Registration failed", List.of("Email may already exist")), HttpStatus.BAD_REQUEST);
        }
        LoginResponse loginResponse = LoginAndGetResponse(new LoginUserDto(registeredUser.getEmail(), registerUserDto.getPassword()));
        return new ResponseEntity<>(loginResponse,  HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto loginUserDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            return new ResponseEntity<>(new ErrorResponse("Already logged in"), HttpStatus.BAD_REQUEST);
        }
        LoginResponse loginResponse = LoginAndGetResponse(loginUserDto);
        return ResponseEntity.ok(loginResponse);
    }
}
