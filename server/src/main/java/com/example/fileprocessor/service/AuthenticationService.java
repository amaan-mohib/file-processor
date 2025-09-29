package com.example.fileprocessor.service;

import com.example.fileprocessor.payload.request.LoginUserDto;
import com.example.fileprocessor.payload.request.RegisterUserDto;
import com.example.fileprocessor.entity.User;
import com.example.fileprocessor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User register(RegisterUserDto payload) {
        User user = new User();
        user.setEmail(payload.getEmail());
        user.setPassword(passwordEncoder.encode(payload.getPassword()));
        user.setName(payload.getName());

        if (userRepository.findByEmail(payload.getEmail()).isPresent()) {
            return null;
        }

        return userRepository.save(user);
    }

    public User login(LoginUserDto payload) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(payload.getEmail(), payload.getPassword())
        );

        return userRepository.findByEmail(payload.getEmail()).orElseThrow();
    }
}
