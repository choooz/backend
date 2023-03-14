package com.example.manymanyUsers.token.service;

import com.example.manymanyUsers.config.jwt.JwtTokenProvider;
import com.example.manymanyUsers.exception.token.TokenNotFoundException;
import com.example.manymanyUsers.token.domain.TokenEntity;
import com.example.manymanyUsers.token.domain.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private int monthToMinites = 43200;
    private int accessTokenExpriedTime = 30;

    /**
     * 엑세스 토큰 재발급 로직
     * @param refreshtoken
     * @return accessToken
     */
    public String refreshAcessToken(String refreshtoken) {
        TokenEntity tokenEntity = tokenRepository.findByRefreshToken(refreshtoken).orElseThrow(TokenNotFoundException::new);
        String newAcessToken = jwtTokenProvider.makeJwtToken(tokenEntity.getUserId(), accessTokenExpriedTime);
        return newAcessToken;
    }

    /**
     * 리프레쉬 토큰 갱신 로직
     * @param  refreshtoken
     * @return refreshToken
     */
    public String updateRefreshToken(String refreshtoken) {
        TokenEntity tokenEntity = tokenRepository.findByRefreshToken(refreshtoken).orElseThrow(TokenNotFoundException::new);
        String newRefreshToken = jwtTokenProvider.makeJwtToken(tokenEntity.getUserId(), monthToMinites);
        tokenEntity.updateRefreshToken(newRefreshToken);
        tokenRepository.save(tokenEntity);
        return newRefreshToken;
    }
}
