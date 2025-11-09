package com.example.fileprocessor.service;

import com.example.fileprocessor.entity.RefreshToken;
import com.example.fileprocessor.entity.User;
import com.example.fileprocessor.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${security.jwt.refresh-expiration}")
    private Long refreshTokenDuration;

    private final RefreshTokenRepository refreshTokenRepository;

    public Optional<RefreshToken> getRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public void delete(RefreshToken token) {
        refreshTokenRepository.delete(token);
    }

    @Transactional
    public RefreshToken create(User user) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId()).orElse(new RefreshToken());
        refreshToken.setUser(user);
        refreshToken.setExpiredAt(Instant.now().plusMillis(refreshTokenDuration));
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(refreshToken);
    }

    public boolean isExpired(RefreshToken token) {
        return token.getExpiredAt().isBefore(Instant.now());
    }
}
