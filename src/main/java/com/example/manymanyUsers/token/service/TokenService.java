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

    final private int MONTH_TO_MINITES = 43200;
    final private int ACCESS_TOKEN_EXPIREDTIME = 30;

    /**
     * 엑세스 토큰 재발급 로직
     * @param refreshtoken
     * @return accessToken
     */
    public String refreshAcessToken(String refreshtoken) {
        TokenEntity tokenEntity = tokenRepository.findByRefreshToken(refreshtoken).orElseThrow(TokenNotFoundException::new);
        String newAcessToken = jwtTokenProvider.makeJwtToken(tokenEntity.getUserId(), ACCESS_TOKEN_EXPIREDTIME);
        return newAcessToken;
    }

    /**
     * 리프레쉬 토큰 갱신 로직
     * @param  refreshtoken
     * @return refreshToken
     */
    public String updateRefreshToken(String refreshtoken) {
        TokenEntity tokenEntity = tokenRepository.findByRefreshToken(refreshtoken).orElseThrow(TokenNotFoundException::new);
        String newRefreshToken = jwtTokenProvider.makeJwtToken(tokenEntity.getUserId(), MONTH_TO_MINITES);
        tokenEntity.updateRefreshToken(newRefreshToken);
        tokenRepository.save(tokenEntity);
        return newRefreshToken;
    }

    /**
     * 새로 로그인시 리프레쉬 토큰 발급
     * @param userId
     * @return refreshToken
     */
    public String issueRefreshToken(Long userId) {
        String newRefreshToken = jwtTokenProvider.makeJwtToken(userId, MONTH_TO_MINITES);
        TokenEntity tokenEntity = new TokenEntity(newRefreshToken, userId);
        tokenRepository.save(tokenEntity);
        return newRefreshToken;
    }
}
