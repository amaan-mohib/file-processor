package com.example.fileprocessor.controller;

import com.example.fileprocessor.entity.RefreshToken;
import com.example.fileprocessor.payload.request.LoginUserDto;
import com.example.fileprocessor.payload.request.RefreshTokenDto;
import com.example.fileprocessor.payload.request.RegisterUserDto;
import com.example.fileprocessor.entity.User;
import com.example.fileprocessor.payload.response.ErrorResponse;
import com.example.fileprocessor.payload.response.GenericResponse;
import com.example.fileprocessor.payload.response.LoginResponse;
import com.example.fileprocessor.service.AuthenticationService;
import com.example.fileprocessor.service.JwtService;
import com.example.fileprocessor.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationService authenticationService;

    private LoginResponse loginAndGetResponse(LoginUserDto loginUserDto) {
        User user = authenticationService.login(loginUserDto);
        String jwtToken = jwtService.generateToken(user.getEmail());
        RefreshToken refreshToken = refreshTokenService.create(user);
        return new LoginResponse(jwtToken, refreshToken.getToken(), jwtService.getExpiration());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.register(registerUserDto);
        if (registeredUser == null) {
            return new ResponseEntity<>(new ErrorResponse("Registration failed", List.of("Email may already exist")), HttpStatus.BAD_REQUEST);
        }
        LoginResponse loginResponse = loginAndGetResponse(new LoginUserDto(registeredUser.getEmail(), registerUserDto.getPassword()));
        return new ResponseEntity<>(loginResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto loginUserDto) {
        LoginResponse loginResponse = loginAndGetResponse(loginUserDto);
        return ResponseEntity.ok(loginResponse);
    }

    @Transactional
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        RefreshToken refreshToken = refreshTokenService.getRefreshToken(refreshTokenDto.refreshToken())
                .orElseThrow();

        if (refreshTokenService.isExpired(refreshToken)) {
            return new ResponseEntity<>(new ErrorResponse("Refresh token expired", List.of("Please login again")), HttpStatus.UNAUTHORIZED);
        }

        String jwtToken = jwtService.generateToken(refreshToken.getUser().getEmail());
        LoginResponse loginResponse = new LoginResponse(jwtToken, refreshToken.getToken(), jwtService.getExpiration());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        RefreshToken refreshToken = refreshTokenService.getRefreshToken(refreshTokenDto.refreshToken())
                .orElseThrow();
        refreshTokenService.delete(refreshToken);
        return ResponseEntity.ok(new GenericResponse<>("Logged out successfully", 200));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElement(NoSuchElementException exc) {
        log.error("e: ", exc);
        return new ResponseEntity<>(new GenericResponse<>("Invalid refresh token", 404), HttpStatus.NOT_FOUND);
    }
}
