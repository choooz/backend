package kr.co.chooz.user.service;

import kr.co.chooz.token.domain.TokenGenerator;
import kr.co.chooz.user.domain.UserRegister;
import kr.co.chooz.user.domain.thirdParty.ThirdPartyAuthorizer;
import kr.co.chooz.user.domain.thirdParty.ThirdPartyAuthorizerProvider;
import kr.co.chooz.user.dto.LoginToken;
import kr.co.chooz.user.dto.SocialLoginRequest;
import kr.co.chooz.user.dto.ThirdPartySignupInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final UserRegister userRegister;
    private final ThirdPartyAuthorizerProvider thirdPartyAuthorizerProvider;
    private final TokenGenerator tokenGenerator;

    public void signup(SocialLoginRequest socialLoginRequest) {
        userRegister.register(socialLoginRequest.getProviderId(), socialLoginRequest.getProviderType());
    }

    public LoginToken signupByThirdParty(ThirdPartySignupInfo signupInfo) {
        ThirdPartyAuthorizer authorizer = thirdPartyAuthorizerProvider.get(signupInfo.getProviderType());
        String accessToken = authorizer.getAccessToken(signupInfo);
        Map<String, String> userInfo = authorizer.getUserInfo(accessToken);
        String providerId = userInfo.get("id");

        boolean isNewUser = userRegister.registerIfNeed(providerId, signupInfo.getProviderType());

        return tokenGenerator.generate(providerId, isNewUser);
    }
}
