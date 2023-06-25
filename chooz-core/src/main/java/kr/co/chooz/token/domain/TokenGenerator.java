package kr.co.chooz.token.domain;

import kr.co.chooz.user.domain.UserFinder;
import kr.co.chooz.user.domain.entitiy.User;
import kr.co.chooz.user.dto.LoginToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 엑세스 토큰, 리프레쉬 토큰 발급하는 애
 */
@Component
@RequiredArgsConstructor
public class TokenGenerator {

    private final UserFinder userFinder;
    private final JwtTokenProvider jwtTokenProvider;

    private final int MONTH_TO_MINITES = 43200;
    private final int ACCESS_TOKEN_EXPIREDTIME = 30;

    public LoginToken generate(String providerId, boolean isNewUser) {
        User user = userFinder.findByProviderId(providerId);

        return new LoginToken(
                generateAccessToken(user.getId()),
                generateRefreshToken(user.getId()),
                isNewUser
        );
    }

    private String generateRefreshToken(Long userId) {
        return jwtTokenProvider.makeJwtToken(userId, MONTH_TO_MINITES);
    }

    private String generateAccessToken(Long userId) {
        return jwtTokenProvider.makeJwtToken(userId, ACCESS_TOKEN_EXPIREDTIME);
    }

}
