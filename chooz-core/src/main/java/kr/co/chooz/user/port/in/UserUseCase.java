package kr.co.chooz.user.port.in;

import kr.co.chooz.user.dto.LoginToken;
import kr.co.chooz.user.dto.ThirdPartySignupInfo;
import kr.co.chooz.user.service.ManageService;
import kr.co.chooz.user.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUseCase {

    private final RegisterService registerService;
    private final ManageService manageService;

    public LoginToken signupByThirdParty(ThirdPartySignupInfo signupInfo) {
        return registerService.signupByThirdParty(signupInfo);
    }

    public void addUserInfo(Long userId) {
        manageService.addUserInfo(userId);
    }
}
