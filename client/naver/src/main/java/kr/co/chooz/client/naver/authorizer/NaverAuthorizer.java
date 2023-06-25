package kr.co.chooz.client.naver.authorizer;

import kr.co.chooz.client.naver.request.BearerAuthHeader;
import kr.co.chooz.client.naver.response.NaverTokenResponse;
import kr.co.chooz.client.naver.response.NaverUserInfo;
import kr.co.chooz.user.domain.entitiy.ProviderType;
import kr.co.chooz.user.domain.thirdParty.ThirdPartyAuthorizer;
import kr.co.chooz.user.dto.ThirdPartySignupInfo;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
