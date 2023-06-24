package kr.co.chooz.user.domain.thirdParty;

import kr.co.chooz.user.domain.entitiy.ProviderType;
import kr.co.chooz.user.dto.ThirdPartySignupInfo;

import java.util.Map;

// 서드파티 제공자의 인증을 처리하는 인터페이스
public interface ThirdPartyAuthorizer {
    String getAccessToken(ThirdPartySignupInfo signupInfo);

    Map<String, String> getUserInfo(String accessToken);

    ProviderType getProviderType();

}