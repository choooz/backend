package com.example.manymanyUsers.token.service;

import com.example.manymanyUsers.config.jwt.JwtTokenProvider;
import com.example.manymanyUsers.token.domain.TokenEntity;
import com.example.manymanyUsers.token.domain.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String issueRefreshToken(Long userId) {
        String token = jwtTokenProvider.makeJwtToken(userId, 3600);
        TokenEntity tokenEntity = new TokenEntity(token, userId);
        tokenRepository.save(tokenEntity);
        return token;
    }
}
