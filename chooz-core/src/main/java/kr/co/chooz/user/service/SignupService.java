package kr.co.chooz.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupService {
    private final UserRegister userRegister;
    private final ThirdPartyAuthorizerProvider thirdPartyAuthorizerProvider;
    private final TokenGenerator tokenGenerator;

    public void signup(GeneralSignupInfo signupInfo) {
        userRegister.register(signupInfo);
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