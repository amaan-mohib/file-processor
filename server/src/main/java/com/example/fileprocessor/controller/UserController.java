package com.example.fileprocessor.controller;

import com.example.fileprocessor.dto.response.UserResponse;
import com.example.fileprocessor.entity.User;
import com.example.fileprocessor.repository.UserRepository;
import com.example.fileprocessor.dto.response.GenericResponse;
import com.example.fileprocessor.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<?> authenticatedUser() {
        User currentUser = SecurityUtil.getCurrentUser();
        UserResponse userResponse = new UserResponse(currentUser);
        return new ResponseEntity<>(new GenericResponse<>("Current user", 200, userResponse), HttpStatus.OK);
    }
}
