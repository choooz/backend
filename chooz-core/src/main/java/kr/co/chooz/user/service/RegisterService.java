package kr.co.chooz.user.service;

import kr.co.chooz.token.domain.TokenGenerator;
import kr.co.chooz.user.domain.UserRegister;
import kr.co.chooz.user.domain.thirdParty.ThirdPartyAuthorizer;
import kr.co.chooz.user.domain.thirdParty.ThirdPartyAuthorizerProvider;
import kr.co.chooz.user.dto.LoginToken;
import kr.co.chooz.user.dto.SocialLoginInfo;
import kr.co.chooz.user.dto.ThirdPartySignupInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterService {
    private final UserRegister userRegister;
    private final ThirdPartyAuthorizerProvider thirdPartyAuthorizerProvider;
    private final TokenGenerator tokenGenerator;

    public void signup(SocialLoginInfo socialLoginInfo) {
        userRegister.register(socialLoginInfo.getProviderId(), socialLoginInfo.getProviderType());
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
