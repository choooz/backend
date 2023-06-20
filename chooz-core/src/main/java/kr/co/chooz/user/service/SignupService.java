package kr.co.chooz.user.service;

import kr.co.chooz.user.domain.ProviderType;
import kr.co.chooz.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupService {
//    private final UserRepository userRepository;

    /**
     * 일반 회원 가입
     * @param signupInfo
     */
//    public void register(GeneralSignupInfo signupInfo) {
//        if (userRepository.existsByProviderId(signupInfo.getEmail())) {
//            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
//        }
//
//        User user = new User(signupInfo);
//        userRepository.register(user);
//    }

    /**
     * 소셜 회원 가입
     *
     * @param providerId
     * @param providerType
     * @return 새로 가입한 유저인지 아닌지
     */
//    public boolean registerIfNeed(String providerId, ProviderType providerType) {
//        if (userRepository.existsByProviderId(providerId)) {
//            return false;
//        }
//        User user = new User(providerId, providerType);
//        userRepository.register(user);
//        return true;
//    }
}