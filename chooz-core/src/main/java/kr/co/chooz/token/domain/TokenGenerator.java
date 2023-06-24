package kr.co.chooz.token.domain;

import kr.co.chooz.token.dto.TokenGenerateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 엑세스 토큰, 리프레쉬 토큰 발급하는 애
 */
@Component
@RequiredArgsConstructor
public class TokenGenerator {

    private final JwtTokenProvider jwtTokenProvider;
    private static final int ACCESS_TOKEN_VALIDITY_PERIOD = 30;
    private static final int REFESH_TOKEN_VALIDITY_PERIOD = 43200;

    public TokenGenerateResponse generateToken(Long userId) {
        String accessToken = generateAccessToken(userId);
        String refeshToken = generateRefeshToken(userId);
        return new TokenGenerateResponse(accessToken, refeshToken);
    }

    private String generateAccessToken(Long userId) {
        return jwtTokenProvider.makeJwtToken(userId, ACCESS_TOKEN_VALIDITY_PERIOD);
    }

    private String generateRefeshToken(Long userId) {
        return jwtTokenProvider.makeJwtToken(userId, REFESH_TOKEN_VALIDITY_PERIOD);
    }





}
