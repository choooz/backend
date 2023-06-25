package kr.co.chooz.client.naver.authorizer;

import kr.co.chooz.client.naver.request.BearerAuthHeader;
import kr.co.chooz.client.naver.response.NaverTokenResponse;
import kr.co.chooz.client.naver.response.NaverUserInfo;
import kr.co.chooz.user.domain.entitiy.ProviderType;
import kr.co.chooz.user.domain.thirdParty.ThirdPartyAuthorizer;
import kr.co.chooz.user.dto.ThirdPartySignupInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NaverAuthorizer implements ThirdPartyAuthorizer {

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String client_secret;

    private final NaverAuthClient naverAuthClient;
    private final NaverApiClient naverApiClient;

    @Override
    public String getAccessToken(ThirdPartySignupInfo signupInfo) {

        Map<String, String> propertiesValues = signupInfo.getPropertiesValues();

        NaverTokenResponse response = naverAuthClient.generateToken(
                "authorization_code",
                clientId,
                client_secret,
                propertiesValues.get("code"),
                propertiesValues.get("state")
        );

        return response.getAccess_token();
    }

    @Override
    public Map<String, String> getUserInfo(String accessToken) {

        NaverUserInfo naverUserInfo = naverApiClient.getUserInfo(new BearerAuthHeader(accessToken).getAuthorization());

        Map<String, String> result = new HashMap<>();
        result.put("id", naverUserInfo.getId().toString());
        result.put("nickname", naverUserInfo.getNickName());
        result.put("profile_image", naverUserInfo.getProfileImage());

        return result;
    }

    @Override
    public ProviderType getProviderType() {
        return ProviderType.NAVER;
    }

}
